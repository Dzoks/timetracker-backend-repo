package rs.dzoks.timetracker.repository.repositoryCustom;

import rs.dzoks.timetracker.model.Timesheet;

import java.util.List;

public interface TimesheetRepositoryCustom {
    List<Timesheet> getByUser(Integer userId);
    List<Timesheet> getByUserAndProject(Integer userId, Integer projectId);
}
