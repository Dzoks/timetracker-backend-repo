package rs.dzoks.timetracker.model.modelCustom;

import rs.dzoks.timetracker.model.UserHasProject;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.math.BigDecimal;

@SqlResultSetMapping(name = "UserHasProjectUserTimesheetMapping",
        classes = @ConstructorResult(
                targetClass = UserHasProjectUserTimesheet.class,
                columns ={
                        @ColumnResult(name = "id"),
                        @ColumnResult(name = "user_id"),
                        @ColumnResult(name = "project_id"),
                        @ColumnResult(name = "first_name"),
                        @ColumnResult(name = "last_name"),
                        @ColumnResult(name = "hour_rate"),
                        @ColumnResult(name = "total_hours",type = Integer.class),
                        @ColumnResult(name = "total_turnover"),
                        @ColumnResult(name = "blocked")
                } )
)
@MappedSuperclass
public class UserHasProjectUserTimesheet extends UserHasProject {
    private String firstName;
    private String lastName;
    private BigDecimal totalTurnover;
    private Integer totalHours;

    public UserHasProjectUserTimesheet(){

    }
    public UserHasProjectUserTimesheet(Integer id,Integer userId,Integer projectId,String firstName,String lastName, BigDecimal hourRate,Integer totalHours,BigDecimal totalTurnover,Byte blocked){
        setId(id);
        setUserId(userId);
        setProjectId(projectId);
        setHourRate(hourRate);
        setBlocked(blocked);
        this.firstName=firstName;
        this.lastName=lastName;
        this.totalHours=totalHours;
        this.totalTurnover=totalTurnover;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getTotalTurnover() {
        return totalTurnover;
    }

    public void setTotalTurnover(BigDecimal totalTurnover) {
        this.totalTurnover = totalTurnover;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }
}
