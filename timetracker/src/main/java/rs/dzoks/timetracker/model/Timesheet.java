package rs.dzoks.timetracker.model;

import rs.dzoks.timetracker.model.modelCustom.TimesheetProject;
import rs.dzoks.timetracker.model.modelOther.LineChartModel;
import rs.dzoks.timetracker.model.modelOther.PieChartModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SqlResultSetMapping(name = "PieChartMapping",
        classes = @ConstructorResult(targetClass = PieChartModel.class,
                columns = {
                        @ColumnResult(name = "id"),
                        @ColumnResult(name = "text"),
                        @ColumnResult(name = "value",type = BigDecimal.class),
                }))
@SqlResultSetMapping(name = "LineChartMapping",
        classes = @ConstructorResult(targetClass = LineChartModel.class,
                columns = {
                        @ColumnResult(name = "date",type = Date.class),
                        @ColumnResult(name = "value",type = BigDecimal.class),
                }))
public class Timesheet {
    private Integer id;
    private Timestamp date;
    private String description;
    private Integer hours;
    private BigDecimal turnover;
    private Timestamp created;
    private Integer userHasProjectId;
    private Byte active;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 45)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "hours", nullable = false)
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @Basic
    @Column(name = "turnover", nullable = false, precision = 0)
    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "user_has_project_id", nullable = false)
    public Integer getUserHasProjectId() {
        return userHasProjectId;
    }

    public void setUserHasProjectId(Integer userHasProjectId) {
        this.userHasProjectId = userHasProjectId;
    }

    @Basic
    @Column(name = "active", nullable = false,insertable = false)
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public Timesheet(){

    }
    public Timesheet(Integer id, Timestamp date, String description, Integer hours, BigDecimal turnover, Timestamp created, Integer userHasProjectId, Byte active) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.hours = hours;
        this.turnover = turnover;
        this.created = created;
        this.userHasProjectId = userHasProjectId;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timesheet timesheet = (Timesheet) o;
        return Objects.equals(id, timesheet.id) &&
                Objects.equals(date, timesheet.date) &&
                Objects.equals(description, timesheet.description) &&
                Objects.equals(hours, timesheet.hours) &&
                Objects.equals(turnover, timesheet.turnover) &&
                Objects.equals(created, timesheet.created) &&
                Objects.equals(userHasProjectId, timesheet.userHasProjectId) &&
                Objects.equals(active, timesheet.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, description, hours, turnover, created, userHasProjectId, active);
    }

    public Timesheet cloneMe(){
        return this;
    }
}
