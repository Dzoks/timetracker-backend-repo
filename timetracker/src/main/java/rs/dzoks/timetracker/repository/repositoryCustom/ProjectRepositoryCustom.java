package rs.dzoks.timetracker.repository.repositoryCustom;

import rs.dzoks.timetracker.model.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<Project> getProjectsForUser(Integer userId);
}
