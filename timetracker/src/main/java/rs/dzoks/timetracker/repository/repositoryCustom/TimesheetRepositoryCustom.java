package rs.dzoks.timetracker.repository.repositoryCustom;

import rs.dzoks.timetracker.model.Timesheet;
import rs.dzoks.timetracker.model.modelCustom.TimesheetProject;
import rs.dzoks.timetracker.model.modelOther.LineChartModel;
import rs.dzoks.timetracker.model.modelOther.PieChartModel;

import java.util.List;

public interface TimesheetRepositoryCustom {
    List<TimesheetProject> getByUser(Integer userId);
    List<Timesheet> getByUserAndProject(Integer userId, Integer projectId);
    List<PieChartModel> getTurnoverForProjectByUsers(Integer projectId);
    List<PieChartModel> getHoursForProjectByUsers(Integer projectId);
    List<LineChartModel> getTurnoverForProject(Integer projectId);
    List<LineChartModel> getHoursForProject(Integer projectId);
    List<LineChartModel> getHoursForUser(Integer userId);
    List<LineChartModel> getTurnoverForUser(Integer userId);
    List<PieChartModel> getTurnoverForUserByProjects(Integer userId);
    List<PieChartModel> getHoursForUserByProjects(Integer userId);
}
