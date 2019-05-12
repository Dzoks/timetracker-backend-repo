package rs.dzoks.timetracker.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Project {
    private Integer id;
    private String name;
    private Date startDate;
    private String description;
    private Date estimatedEndDate;
    private Integer estimateedWorkHours;
    private Integer estimatedBudget;
    private Byte finished;
    private Integer projectManagerId;
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
    @Column(name = "estimateed_work_hours", nullable = true)
    public Integer getEstimateedWorkHours() {
        return estimateedWorkHours;
    }

    public void setEstimateedWorkHours(Integer estimateedWorkHours) {
        this.estimateedWorkHours = estimateedWorkHours;
    }

    @Basic
    @Column(name = "estimated_budget", nullable = true, precision = 0)
    public Integer getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(Integer estimatedBudget) {
        this.estimatedBudget = estimatedBudget;
    }

    @Basic
    @Column(name = "finished", nullable = false)
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
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(name, project.name) &&
                Objects.equals(startDate, project.startDate) &&
                Objects.equals(description, project.description) &&
                Objects.equals(estimatedEndDate, project.estimatedEndDate) &&
                Objects.equals(estimateedWorkHours, project.estimateedWorkHours) &&
                Objects.equals(estimatedBudget, project.estimatedBudget) &&
                Objects.equals(finished, project.finished) &&
                Objects.equals(projectManagerId, project.projectManagerId) &&
                Objects.equals(active, project.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, description, estimatedEndDate, estimateedWorkHours, estimatedBudget, finished, projectManagerId, active);
    }
}
