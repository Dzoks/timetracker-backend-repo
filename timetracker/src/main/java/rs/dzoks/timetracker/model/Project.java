package rs.dzoks.timetracker.model;

import com.rits.cloning.Cloner;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Project {
    private Integer id;
    private String name;
    private Date startDate;
    private String description;
    private Date estimatedEndDate;
    private Integer estimatedWorkHours;
    private BigDecimal estimatedBudget;
    private Byte finished;
    private Integer projectManagerId;
    private Byte active;

    @Transient
    private BigDecimal hourRate;
    @Transient
    public BigDecimal getHourRate() {
        return hourRate;
    }
    public void setHourRate(BigDecimal hourRate) {
        this.hourRate = hourRate;
    }

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
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
    @Column(name = "estimated_end_date", nullable = true)
    public Date getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(Date estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    @Basic
    @Column(name = "estimated_work_hours", nullable = true)
    public Integer getEstimatedWorkHours() {
        return estimatedWorkHours;
    }

    public void setEstimatedWorkHours(Integer estimatedWorkHours) {
        this.estimatedWorkHours = estimatedWorkHours;
    }

    @Basic
    @Column(name = "estimated_budget", nullable = true, precision = 0)
    public BigDecimal getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(BigDecimal estimatedBudget) {
        this.estimatedBudget = estimatedBudget;
    }

    @Basic
    @Column(name = "finished", nullable = false,insertable = false)
    public Byte getFinished() {
        return finished;
    }

    public void setFinished(Byte finished) {
        this.finished = finished;
    }

    @Basic
    @Column(name = "project_manager_id", nullable = false)
    public Integer getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Integer projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    @Basic
    @Column(name = "active", nullable = false,insertable = false)
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
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Project(){

    }
    public Project(Integer id, String name, Date startDate, String description, Date estimatedEndDate, Integer estimatedWorkHours, BigDecimal estimatedBudget, Byte finished, Integer projectManagerId, Byte active) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.description = description;
        this.estimatedEndDate = estimatedEndDate;
        this.estimatedWorkHours = estimatedWorkHours;
        this.estimatedBudget = estimatedBudget;
        this.finished = finished;
        this.projectManagerId = projectManagerId;
        this.active = active;
    }

}
