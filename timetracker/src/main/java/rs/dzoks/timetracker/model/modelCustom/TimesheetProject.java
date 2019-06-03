package rs.dzoks.timetracker.model.modelCustom;

import rs.dzoks.timetracker.model.Timesheet;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

@SqlResultSetMapping(name = "TimesheetProjectMapping",
        classes = @ConstructorResult(targetClass = TimesheetProject.class,
                columns = {
                        @ColumnResult(name = "id"),
                        @ColumnResult(name = "date",type = Date.class),
                        @ColumnResult(name = "description"),
                        @ColumnResult(name = "hours"),
                        @ColumnResult(name = "turnover"),
                        @ColumnResult(name = "created",type = Date.class),
                        @ColumnResult(name = "user_has_project_id"),
                        @ColumnResult(name = "active"),
                        @ColumnResult(name = "project_name")
                }))

@MappedSuperclass
public class TimesheetProject extends Timesheet {
    private String projectName;

    public TimesheetProject(Integer id, Date date, String description, Integer hours, BigDecimal turnover, Date created, Integer userHasProjectId, Byte active, String projectName) {
        super(id, date==null?null:new Timestamp(date.getTime()), description, hours, turnover, created==null?null:new Timestamp(created.getTime()), userHasProjectId, active);
        this.projectName = projectName;
    }

    public TimesheetProject(Timesheet timesheet,String projectName){
        super(timesheet.getId(), timesheet.getDate(),timesheet.getDescription(),timesheet.getHours(),timesheet.getTurnover(),timesheet.getCreated(),timesheet.getUserHasProjectId(),timesheet.getActive());
        this.projectName=projectName;
    }



    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
