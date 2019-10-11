package rs.dzoks.timetracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.dzoks.timetracker.common.ForbiddenException;
import rs.dzoks.timetracker.common.GenericController;
import rs.dzoks.timetracker.common.UserGroupMap;
import rs.dzoks.timetracker.model.modelOther.LineChartModel;
import rs.dzoks.timetracker.model.modelOther.PieChartModel;
import rs.dzoks.timetracker.repository.TimesheetRepository;
import rs.dzoks.timetracker.session.UserBean;

import java.util.List;

@RequestMapping("hub/chart")
@RestController
@Scope("session")
public class ChartController extends GenericController {

    private final TimesheetRepository timesheetRepository;

    @Autowired
    public ChartController(UserBean userBean, UserGroupMap userGroupMap, TimesheetRepository timesheetRepository) {
        super(userBean, userGroupMap);
        this.timesheetRepository = timesheetRepository;
    }

    @GetMapping("/turnover/{projectId}/users")
    public List<PieChartModel> getTurnoverForProjectByUsers(@PathVariable Integer projectId) throws ForbiddenException {
        if (userGroupMap.getByKey("projectManager").equals(userBean.getUser().getUserGroupId()))
            return timesheetRepository.getTurnoverForProjectByUsers(projectId);
        else
            throw new ForbiddenException("Forbidden");
    }

    @GetMapping("/hours/{projectId}/users")
    public List<PieChartModel> getHoursForProjectByUsers(@PathVariable Integer projectId) throws ForbiddenException {
        if (userGroupMap.getByKey("projectManager").equals(userBean.getUser().getUserGroupId()))
            return timesheetRepository.getHoursForProjectByUsers(projectId);
        else
            throw new ForbiddenException("Forbidden");
    }

    @GetMapping("/hours/{projectId}")
    public List<LineChartModel> getHoursForProject(@PathVariable Integer projectId) throws ForbiddenException {
        if (userGroupMap.getByKey("projectManager").equals(userBean.getUser().getUserGroupId()))
            return timesheetRepository.getHoursForProject(projectId);
        else
            throw new ForbiddenException("Forbidden");
    }

    @GetMapping("/turnover/{projectId}")
    public List<LineChartModel> getTurnoverForProject(@PathVariable Integer projectId) throws ForbiddenException {
        if (userGroupMap.getByKey("projectManager").equals(userBean.getUser().getUserGroupId()))
            return timesheetRepository.getTurnoverForProject(projectId);
        else
            throw new ForbiddenException("Forbidden");
    }

    @GetMapping("/turnover")
    public List<LineChartModel> getTurnover(){
        return timesheetRepository.getTurnoverForUser(userBean.getUser().getId());
    }
    @GetMapping("/hours")
    public List<LineChartModel> getHours(){
        return timesheetRepository.getHoursForUser(userBean.getUser().getId());
    }

    @GetMapping("/turnover/projects")
    public List<PieChartModel> getTurnoverByProjects(){
        return timesheetRepository.getTurnoverForUserByProjects(userBean.getUser().getId());
    }
    @GetMapping("/hours/projects")
    public List<PieChartModel> getHoursByProjects(){
        return timesheetRepository.getHoursForUserByProjects(userBean.getUser().getId());
    }




}
