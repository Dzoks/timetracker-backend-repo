package rs.dzoks.timetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.common.BadRequestException;
import rs.dzoks.timetracker.model.User;
import rs.dzoks.timetracker.model.UserHasProject;
import rs.dzoks.timetracker.model.modelCustom.UserHasProjectUserTimesheet;
import rs.dzoks.timetracker.repository.TimesheetRepository;
import rs.dzoks.timetracker.repository.UserHasProjectRepository;
import rs.dzoks.timetracker.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("hub/userHasProject")
@Scope("session")
public class UserHasProjectController {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    String handleException(BadRequestException e) {
        return e.getMessage();
    }
    private final UserHasProjectRepository userHasProjectRepository;

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserHasProjectController(UserHasProjectRepository userHasProjectRepository) {
        this.userHasProjectRepository = userHasProjectRepository;
    }

    @GetMapping("/{projectId}")
    List<UserHasProjectUserTimesheet> getByProject(@PathVariable Integer projectId){
        return userHasProjectRepository.getAllByProject(projectId);
    }

    @PostMapping
    @Transactional
    UserHasProjectUserTimesheet insert(@RequestBody UserHasProject uhp) throws BadRequestException {
        uhp=userHasProjectRepository.saveAndFlush(uhp);
        if (uhp==null)
            throw new BadRequestException("insertFail");
        User user=userRepository.findById(uhp.getUserId()).orElse(null);
        if (user==null || user.getActive().equals((byte)0))
            throw new BadRequestException("insertFail");
        UserHasProjectUserTimesheet uhput=new UserHasProjectUserTimesheet();
        uhput.setId(uhp.getId());
        uhput.setBlocked(uhp.getBlocked());
        uhput.setHourRate(uhp.getHourRate());
        uhput.setProjectId(uhp.getProjectId());
        uhput.setUserId(uhp.getUserId());
        uhput.setActive(uhp.getActive());
        uhput.setFirstName(user.getFirstName());
        uhput.setLastName(user.getLastName());
        return uhput;
    }

    @DeleteMapping("/id")
    @Transactional
    UserHasProject delete(@PathVariable Integer id) throws BadRequestException {
        UserHasProject uhp=userHasProjectRepository.findById(id).orElse(null);
        if (uhp==null || uhp.getActive().equals((byte)0))
            throw new BadRequestException("deleteFail");
        int count=timesheetRepository.countAllByUserHasProjectIdAndActive(uhp.getId(),(byte)1);
        if (count>0)
            uhp.setBlocked((byte)1);
        else
            uhp.setActive((byte)0);
        uhp=userHasProjectRepository.saveAndFlush(uhp);
        if (uhp==null)
            throw new BadRequestException("deleteFail");
        return uhp;

    }
}
