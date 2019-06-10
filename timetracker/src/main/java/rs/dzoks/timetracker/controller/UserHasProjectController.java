package rs.dzoks.timetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.common.BadRequestException;
import rs.dzoks.timetracker.common.ForbiddenException;
import rs.dzoks.timetracker.model.Project;
import rs.dzoks.timetracker.model.User;
import rs.dzoks.timetracker.model.UserHasProject;
import rs.dzoks.timetracker.model.modelCustom.UserHasProjectUserTimesheet;
import rs.dzoks.timetracker.repository.ProjectRepository;
import rs.dzoks.timetracker.repository.TimesheetRepository;
import rs.dzoks.timetracker.repository.UserHasProjectRepository;
import rs.dzoks.timetracker.repository.UserRepository;
import rs.dzoks.timetracker.session.UserBean;

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
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody
    String handleException(ForbiddenException e) {
        return e.getMessage();
    }
    private final UserHasProjectRepository userHasProjectRepository;

    private final TimesheetRepository timesheetRepository;

    private final UserRepository userRepository;
    private final UserBean userBean;
    private final ProjectRepository projectRepository;

    @Autowired
    public UserHasProjectController(UserHasProjectRepository userHasProjectRepository, TimesheetRepository timesheetRepository, UserRepository userRepository, ProjectRepository projectRepository, UserBean userBean) {
        this.userHasProjectRepository = userHasProjectRepository;
        this.timesheetRepository = timesheetRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.userBean = userBean;
    }

    @GetMapping("/{projectId}")
    List<UserHasProjectUserTimesheet> getByProject(@PathVariable Integer projectId) throws ForbiddenException {
        Project project=projectRepository.getProjectByIdAndActive(projectId,(byte)1);
        if (project==null || !project.getProjectManagerId().equals(userBean.getUser().getId()))
            throw new ForbiddenException("Forbidden");
        return userHasProjectRepository.getAllByProject(projectId);
    }

    @PutMapping("/unblock/{id}")
    public boolean unblock(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
        UserHasProject uhp=userHasProjectRepository.findById(id).orElse(null);
        if (uhp==null || uhp.getActive().equals((byte)0) || uhp.getBlocked().equals((byte)0))
            throw new BadRequestException("deleteFail");
        Project project=projectRepository.getProjectByIdAndActive(uhp.getProjectId(),(byte)1);
        if (project==null || !project.getProjectManagerId().equals(userBean.getUser().getId()))
            throw new ForbiddenException("Forbidden");
        uhp.setBlocked((byte)0);
        uhp=userHasProjectRepository.saveAndFlush(uhp);
        if (uhp==null)
            throw new BadRequestException("deleteFail");
        return true;
    }

    @PutMapping
    public Boolean update(@RequestBody UserHasProject project) throws ForbiddenException {
        UserHasProject projectDb=userHasProjectRepository.getUserHasProjectByIdAndActive(project.getId(),(byte)1);
        if (projectDb==null)
            return false;
        Project projectBase=projectRepository.getProjectByIdAndActive(projectDb.getProjectId(),(byte)1);
        if (projectBase==null || !projectBase.getProjectManagerId().equals(userBean.getUser().getId()))
            throw new ForbiddenException("Forbidden");
        projectDb.setHourRate(project.getHourRate());
        return userHasProjectRepository.saveAndFlush(projectDb)!=null;

    }


    @PostMapping
    @Transactional
    UserHasProjectUserTimesheet insert(@RequestBody UserHasProject uhp) throws BadRequestException, ForbiddenException {
        uhp=userHasProjectRepository.saveAndFlush(uhp);
        if (uhp==null)
            throw new BadRequestException("insertFail");
        Project project=projectRepository.getProjectByIdAndActive(uhp.getProjectId(),(byte)1);
        if (project==null || !project.getProjectManagerId().equals(userBean.getUser().getId()))
            throw new ForbiddenException("Forbidden");
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

    @DeleteMapping("/{id}")
    @Transactional
    UserHasProject delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
        UserHasProject uhp=userHasProjectRepository.findById(id).orElse(null);
        if (uhp==null || uhp.getActive().equals((byte)0))
            throw new BadRequestException("deleteFail");
        Project project=projectRepository.getProjectByIdAndActive(uhp.getProjectId(),(byte)1);
        if (project==null || !project.getProjectManagerId().equals(userBean.getUser().getId()))
            throw new ForbiddenException("Forbidden");
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
