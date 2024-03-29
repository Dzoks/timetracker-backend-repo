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
    private Byte finished;
    private Date estimatedEndDate;
    private Date endDate;
    private BigDecimal budget;
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
    @Column(name = "finished", nullable = false,insertable = false)
    public Byte getFinished() {
        return finished;
    }

    public void setFinished(Byte finished) {
        this.finished = finished;
    }

    @Basic
    @Column(name = "estimated_end_date")
    public Date getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(Date estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    @Basic
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "budget")
    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
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
    public Project(Integer id, String name, Date startDate, String description, Byte finished, Integer projectManagerId, Byte active) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.description = description;
        this.finished = finished;
        this.projectManagerId = projectManagerId;
        this.active = active;
    }

}
