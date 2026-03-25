package rs.dzoks.timetracker.controller;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.common.BadRequestException;
import rs.dzoks.timetracker.common.ForbiddenException;
import rs.dzoks.timetracker.common.GenericController;
import rs.dzoks.timetracker.common.UserGroupMap;
import rs.dzoks.timetracker.model.User;
import rs.dzoks.timetracker.model.modelOther.ChangePasswordModel;
import rs.dzoks.timetracker.repository.ProjectRepository;
import rs.dzoks.timetracker.repository.UserRepository;
import rs.dzoks.timetracker.session.UserBean;
import rs.dzoks.timetracker.util.Notification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("hub/user")
@Scope("request")
public class UserController extends GenericController {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final Notification notification;

    @Autowired
    public UserController(UserBean userBean, UserGroupMap userGroupMap, UserRepository userRepository, ProjectRepository projectRepository, Notification notification) {
        super(userBean, userGroupMap);
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.notification = notification;
    }



    @GetMapping("/state")
    public UserBean getState() {
        return userBean;
    }

    @PostMapping("/login")
    public UserBean login(@RequestBody User loginInfo) {
        User user = userRepository.getUserByEmailAndActive(loginInfo.getEmail(), (byte) 1);
        boolean verified = false;
        if (user != null) {
            try {
                verified = verifyPassword(loginInfo.getPassword(), user.getPassword());
            } catch (IllegalArgumentException e) {
                verified = false;
            }
        }
        if (user != null && verified) {
            userBean.setAuthorized(true);
            userBean.setUser(user);
        }
        return getState();
    }

    @PutMapping("/changePassword")
    public boolean changePassword(@RequestBody ChangePasswordModel changePasswordModel) {
        User user = userRepository.getUserByIdAndActive(changePasswordModel.getId(), (byte) 1);
        if (user == null || !user.getId().equals(userBean.getUser().getId()))
            return false;
        try {
            if (!verifyPassword(changePasswordModel.getOldPassword(), user.getPassword())) {
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        user.setPassword(hashPassword(changePasswordModel.getNewPassword()));
        return userRepository.saveAndFlush(user) != null;
    }


    @GetMapping(value = "/logout")
    public boolean logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        return true;
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.getAllByActive((byte) 1);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/availableFor/{projectId}")
    public List<User> getAvailableFor(@PathVariable Integer projectId) throws ForbiddenException {
        if (!userBean.getUser().getUserGroupId().equals(userGroupMap.getByKey("projectManager")))
            throw new ForbiddenException("Unauthorized");
        return userRepository.getAvailableFor(projectId);
    }

    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int PBKDF2_ITERATIONS = 210000;
    private static final int SALT_LENGTH_BYTES = 32;
    private static final int KEY_LENGTH_BYTES = 64;

    private String hashPassword(String plainText) {
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        new SecureRandom().nextBytes(salt);
        byte[] dk = deriveKey(plainText.toCharArray(), salt, PBKDF2_ITERATIONS, KEY_LENGTH_BYTES);
        String saltB64 = Base64.getEncoder().encodeToString(salt);
        String dkB64 = Base64.getEncoder().encodeToString(dk);
        return "$pbkdf2$sha512$" + PBKDF2_ITERATIONS + "$" + saltB64 + "$" + dkB64;
    }

    private boolean verifyPassword(String plainText, String stored) {
        if (stored == null || !stored.startsWith("$pbkdf2$sha512$")) {
            throw new IllegalArgumentException("Unsupported password hash format. Password reset required.");
        }
        String[] parts = stored.split("\\$");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid password hash format.");
        }
        int iterations = Integer.parseInt(parts[3]);
        byte[] salt = Base64.getDecoder().decode(parts[4]);
        byte[] expected = Base64.getDecoder().decode(parts[5]);
        byte[] actual = deriveKey(plainText.toCharArray(), salt, iterations, expected.length);
        return MessageDigest.isEqual(expected, actual);
    }

    private byte[] deriveKey(char[] password, byte[] salt, int iterations, int keyLengthBytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLengthBytes * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("PBKDF2 failure", e);
        }
    }

    @DeleteMapping("/{userId}")
    public Boolean deleteUser(@PathVariable Integer userId) throws BadRequestException, ForbiddenException {
        if (!userBean.getUser().getUserGroupId().equals(userGroupMap.getByKey("projectManager")))
            throw new ForbiddenException("Forbidden");
        User user = userRepository.getUserByIdAndActive(userId, (byte) 1);
        if (user == null)
            return false;
        if (projectRepository.existsByProjectManagerIdAndFinishedAndActive(user.getId(), (byte) 0, (byte) 1))
            throw new BadRequestException("Korisnik je rukovodilac na nezavršenom projektu");
        user.setActive((byte) 0);
        return userRepository.saveAndFlush(user) != null;
    }

    @PutMapping
    public Boolean update(@RequestBody User user) throws BadRequestException, ForbiddenException {
        if (!userBean.getUser().getUserGroupId().equals(userGroupMap.getByKey("projectManager")))
            throw new ForbiddenException("Forbidden");
        User userDb = userRepository.getUserByIdAndActive(user.getId(), (byte) 1);
        if (userDb == null)
            return false;
        User existingEmailUser=userRepository.getUserByEmailAndActive(user.getEmail(), (byte) 1);
        if (!existingEmailUser.getId().equals(userDb.getId()))
            throw new BadRequestException("Postoji korisnik sa unesenim emailom.");
        if (userDb.getUserGroupId().equals(userGroupMap.getByKey("projectManager"))
                && !userDb.getUserGroupId().equals(user.getUserGroupId())
                && projectRepository.existsByProjectManagerIdAndFinishedAndActive(user.getId(), (byte) 0, (byte) 1))
            throw new BadRequestException("Korisnik je rukovodilac na nezavršenom projektu");
        userDb.setEmail(user.getEmail());
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());

        return userRepository.saveAndFlush(userDb) != null;

    }

    @PutMapping("/generatePassword")
    public Boolean generateNewPassword(@RequestBody User user) throws ForbiddenException, BadRequestException {
        if (!userBean.getUser().getUserGroupId().equals(userGroupMap.getByKey("projectManager")))
            throw new ForbiddenException("Forbidden");
        User userDb=findById(user.getId());
        if (userDb==null)
            throw new BadRequestException("Greška prilikom generisanja lozinke");
        String generatedPassword = RandomStringUtils.randomAlphanumeric(8);
        userDb.setPassword(hashPassword(generatedPassword));
        userDb = userRepository.saveAndFlush(userDb);
        if (userDb == null)
            throw new BadRequestException("Greška prilikom generisanja lozinke.");
        notification.sendMailWithInlineAttachments(userDb.getEmail(), "Timetracker - nova lozinka", getNewPasswordMailBody(userDb, generatedPassword), Collections.singletonList(new Notification.Attachment("image", new ClassPathResource("timetracker-logo.png"))));
        return true;
    }


    @Transactional
    @PostMapping
    public User insert(@RequestBody User user) throws BadRequestException, ForbiddenException {
        if (!userBean.getUser().getUserGroupId().equals(userGroupMap.getByKey("projectManager")))
            throw new ForbiddenException("Forbidden");
        String generatedPassword = RandomStringUtils.randomAlphanumeric(8);
        user.setPassword(hashPassword(generatedPassword));
        if (userRepository.existsUserByEmailAndActive(user.getEmail(), (byte) 1))
            throw new BadRequestException("Postoji korisnik sa unesenim emailom.");

        user = userRepository.saveAndFlush(user);
        if (user == null)
            throw new BadRequestException("Greška prilikom dodavanja.");
        notification.sendMailWithInlineAttachments(user.getEmail(), "Dobrodošli na Timetracker", getInsertMailBody(user, generatedPassword), Collections.singletonList(new Notification.Attachment("image", new ClassPathResource("timetracker-logo.png"))));
        return user;
    }

    private String getNewPasswordMailBody(User user, String generatedPassword) {
        return "<img src='cid:image' alt=\"Timetracker\">\n" +
                "     <div>Pozdrav " + user.getFirstName() + " " + user.getLastName() + ", vaša nova lozinka je <strong>" + generatedPassword + "</strong></div>";
    }

    private String getInsertMailBody(User user, String generatedPassword) {
        return "<img src='cid:image' alt=\"Timetracker\">\n" +
                "     <div>" + user.getFirstName() + " " + user.getLastName() + ", dobrodošli na Timetracker aplikaciju, vaša lozinka je <strong>" + generatedPassword + "</strong></div>";
    }


}
