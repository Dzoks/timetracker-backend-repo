package rs.dzoks.timetracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.common.BadRequestException;
import rs.dzoks.timetracker.model.Timesheet;
import rs.dzoks.timetracker.model.UserHasProject;
import rs.dzoks.timetracker.model.modelCustom.TimesheetProject;
import rs.dzoks.timetracker.repository.ProjectRepository;
import rs.dzoks.timetracker.repository.TimesheetRepository;
import rs.dzoks.timetracker.repository.UserHasProjectRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("hub/timesheet")
@Scope("session")
public class TimesheetController {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    String handleException(BadRequestException e) {
        return e.getMessage();
    }
    private final TimesheetRepository timesheetRepository;

    private final UserHasProjectRepository userHasProjectRepository;

    private final ProjectRepository projectRepository;

    public TimesheetController(TimesheetRepository timesheetRepository, UserHasProjectRepository userHasProjectRepository, ProjectRepository projectRepository) {
        this.timesheetRepository = timesheetRepository;
        this.userHasProjectRepository = userHasProjectRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/byUser/{userId}")
    public List<TimesheetProject> getByUser(@PathVariable Integer userId){
        return timesheetRepository.getByUser(userId);
    }

    @GetMapping("/byUserAndProject/{userId}/{projectId}")
    public List<Timesheet> getByUserAndProject(@PathVariable Integer userId,@PathVariable Integer projectId){
        return timesheetRepository.getByUserAndProject(userId,projectId);
    }

    @DeleteMapping("/{timesheetId}")
    public Boolean deleteTimesheet(@PathVariable Integer timesheetId){
        Timesheet timesheet=timesheetRepository.getTimesheetByIdAndActive(timesheetId,(byte)1);
        if (timesheet==null)
            return false;
        timesheet.setActive((byte)0);
        return timesheetRepository.saveAndFlush(timesheet)!=null;
    }

    @PostMapping
    @Transactional
    public TimesheetProject insert(@RequestBody Timesheet timesheet) throws BadRequestException {
        UserHasProject userHasProject=userHasProjectRepository.findById(timesheet.getUserHasProjectId()).orElse(null);
        if (userHasProject==null)
            throw new BadRequestException("insertFail");
        timesheet.setTurnover(userHasProject.getHourRate().multiply(new BigDecimal(timesheet.getHours())));
        timesheet=timesheetRepository.saveAndFlush(timesheet);
        if (timesheet==null)
            throw new BadRequestException("insertFail");
        String projectName=projectRepository.getProjectByIdAndActive(userHasProject.getProjectId(),(byte)1).getName();
        TimesheetProject tp=new TimesheetProject(timesheet,projectName);
        return tp;
    }

}
