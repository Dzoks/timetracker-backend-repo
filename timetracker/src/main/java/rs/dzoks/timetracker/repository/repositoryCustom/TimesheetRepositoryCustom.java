package rs.dzoks.timetracker.repository.repositoryCustom;

import rs.dzoks.timetracker.model.Timesheet;
import rs.dzoks.timetracker.model.modelCustom.TimesheetProject;

import java.util.List;

public interface TimesheetRepositoryCustom {
    List<TimesheetProject> getByUser(Integer userId);
    List<Timesheet> getByUserAndProject(Integer userId, Integer projectId);
}
