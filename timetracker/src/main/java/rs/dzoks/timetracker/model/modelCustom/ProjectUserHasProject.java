package rs.dzoks.timetracker.model.modelCustom;

import rs.dzoks.timetracker.model.Project;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.math.BigDecimal;
import java.sql.Date;

@SqlResultSetMapping(name = "ProjectUserHasProjectMapping", classes = @ConstructorResult(targetClass = ProjectUserHasProject.class, columns = {
        @ColumnResult(name = "id"),
        @ColumnResult(name = "name"),
        @ColumnResult(name = "start_date", type = java.util.Date.class),
        @ColumnResult(name = "description"),
        @ColumnResult(name = "finished"),
        @ColumnResult(name = "project_manager_id"),
        @ColumnResult(name = "active"),
        @ColumnResult(name = "user_has_project_id"),
        @ColumnResult(name = "total_hours",type = Integer.class),
        @ColumnResult(name = "total_amount",type = BigDecimal.class),
        @ColumnResult(name = "estimated_end_date",type=java.util.Date.class),
        @ColumnResult(name = "end_date",type=java.util.Date.class),
        @ColumnResult(name = "budget",type = BigDecimal.class),


}))

@MappedSuperclass
public class ProjectUserHasProject extends Project {
    private Integer userHasProjectId;
    private Integer totalHours;
    private BigDecimal totalAmount;

    public ProjectUserHasProject(Integer id, String name, java.util.Date startDate, String description, Byte finished, Integer projectManagerId, Byte active, Integer userHasProjectId,Integer totalHours,BigDecimal totalAmount,java.util.Date estimatedEndDate,java.util.Date endDate,BigDecimal budget) {
        super(id, name, startDate == null ? null : new Date(startDate.getTime()), description, finished, projectManagerId, active);
        this.userHasProjectId = userHasProjectId;
        this.totalAmount=totalAmount;
        this.totalHours=totalHours;
        setEndDate(endDate==null?null:new Date(endDate.getTime()));
        setEstimatedEndDate(estimatedEndDate==null?null:new Date(estimatedEndDate.getTime()));
        setBudget(budget);
    }

    public ProjectUserHasProject(Project project, Integer userHasProjectId) {
        super(project.getId(), project.getName(), project.getStartDate(), project.getDescription(), project.getFinished(), project.getProjectManagerId(), project.getActive());
        setEstimatedEndDate(project.getEstimatedEndDate());
        setEndDate(project.getEndDate());
        setBudget(project.getBudget());
        this.userHasProjectId=userHasProjectId;
    }

    public Integer getUserHasProjectId() {
        return userHasProjectId;
    }

    public void setUserHasProjectId(Integer userHasProjectId) {
        this.userHasProjectId = userHasProjectId;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
