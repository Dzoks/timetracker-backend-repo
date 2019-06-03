package rs.dzoks.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dzoks.timetracker.model.Timesheet;
import rs.dzoks.timetracker.repository.repositoryCustom.TimesheetRepositoryCustom;

import java.sql.Time;
import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet,Integer>, TimesheetRepositoryCustom {
    Timesheet getTimesheetByIdAndActive(Integer id,Byte active);
    Integer countAllByUserHasProjectIdAndActive(Integer userHasProjectId,Byte active);
}
