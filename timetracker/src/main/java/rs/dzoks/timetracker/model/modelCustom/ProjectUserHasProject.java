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
        @ColumnResult(name = "estimated_end_date", type = java.util.Date.class),
        @ColumnResult(name = "estimated_work_hours"),
        @ColumnResult(name = "estimated_budget"),
        @ColumnResult(name = "finished"),
        @ColumnResult(name = "project_manager_id"),
        @ColumnResult(name = "active"),
        @ColumnResult(name = "user_has_project_id")
}))

@MappedSuperclass
public class ProjectUserHasProject extends Project {
    private Integer userHasProjectId;

    public ProjectUserHasProject(Integer id, String name, java.util.Date startDate, String description, java.util.Date estimatedEndDate, Integer estimatedWorkHours, BigDecimal estimatedBudget, Byte finished, Integer projectManagerId, Byte active, Integer userHasProjectId) {
        super(id, name, startDate == null ? null : new Date(startDate.getTime()), description, estimatedEndDate == null ? null : new Date(estimatedEndDate.getTime()), estimatedWorkHours, estimatedBudget, finished, projectManagerId, active);
        this.userHasProjectId = userHasProjectId;
    }

    public ProjectUserHasProject(Project project, Integer userHasProjectId) {
        super(project.getId(), project.getName(), project.getStartDate(), project.getDescription(), project.getEstimatedEndDate(), project.getEstimatedWorkHours(), project.getEstimatedBudget(), project.getFinished(), project.getProjectManagerId(), project.getActive());
        this.userHasProjectId=userHasProjectId;
    }

    public Integer getUserHasProjectId() {
        return userHasProjectId;
    }

    public void setUserHasProjectId(Integer userHasProjectId) {
        this.userHasProjectId = userHasProjectId;
    }


}
