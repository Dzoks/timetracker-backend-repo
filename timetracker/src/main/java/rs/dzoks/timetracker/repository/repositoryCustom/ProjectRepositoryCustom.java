package rs.dzoks.timetracker.repository.repositoryCustom;

import rs.dzoks.timetracker.model.Project;
import rs.dzoks.timetracker.model.modelCustom.ProjectUserHasProject;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<ProjectUserHasProject> getProjectsForUser(Integer userId);

    List<ProjectUserHasProject> getProjectsForProjectManager(Integer userId);
}
