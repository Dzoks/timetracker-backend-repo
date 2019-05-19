package rs.dzoks.timetracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.model.Timesheet;
import rs.dzoks.timetracker.repository.TimesheetRepository;

import java.util.List;

@RestController
@RequestMapping("hub/timesheet")
@Scope("session")
public class TimesheetController {

    private final TimesheetRepository timesheetRepository;

    public TimesheetController(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

    @GetMapping("/byUser/{userId}")
    public List<Timesheet> getByUser(@PathVariable Integer userId){
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

}
