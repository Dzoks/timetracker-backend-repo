package rs.dzoks.timetracker.controller;


import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.model.User;
import rs.dzoks.timetracker.repository.UserRepository;
import rs.dzoks.timetracker.session.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("hub/user")
@Scope("request")
public class UserController {

    private final UserRepository userRepository;

    private final UserBean userBean;

    @Autowired
    public UserController(UserRepository userRepository, UserBean userBean) {
        this.userRepository = userRepository;
        this.userBean = userBean;
    }

    @GetMapping("/state")
    public UserBean getState(){
        return userBean;
    }

    @PostMapping("/login")
    public UserBean login(@RequestBody User loginInfo){
        User user=userRepository.getUserByEmailAndPasswordAndActive(loginInfo.getEmail(),hashPassword(loginInfo.getPassword()),(byte)1);
        if (user!=null){
            userBean.setAuthorized(true);
            userBean.setUser(user);
        }
        return getState();
    }

    @GetMapping(value = "/logout")
    public boolean logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        return true;
    }

    @GetMapping
    public List<User> getAll(){
        return userRepository.getAllByActive((byte)1);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id){
        return userRepository.findById(id).orElse(null);
    }

    private String hashPassword( String plainText)  {
        MessageDigest digest= null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            return Hex.encodeHexString(digest.digest(plainText.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

}
