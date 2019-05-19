package rs.dzoks.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dzoks.timetracker.model.Timesheet;
import rs.dzoks.timetracker.repository.repositoryCustom.TimesheetRepositoryCustom;

public interface TimesheetRepository extends JpaRepository<Timesheet,Integer>, TimesheetRepositoryCustom {
    Timesheet getTimesheetByIdAndActive(Integer id,Byte active);
}
