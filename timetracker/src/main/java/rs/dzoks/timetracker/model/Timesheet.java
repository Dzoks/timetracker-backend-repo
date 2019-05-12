package rs.dzoks.timetracker.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Timesheet {
    private Integer id;
    private Date date;
    private String description;
    private Integer hours;
    private Integer turnover;
    private Timestamp created;
    private Integer userHasProjectId;
    private Byte active;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
    public Integer getTurnover() {
        return turnover;
    }

    public void setTurnover(Integer turnover) {
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
    @Column(name = "active", nullable = false)
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
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
}
