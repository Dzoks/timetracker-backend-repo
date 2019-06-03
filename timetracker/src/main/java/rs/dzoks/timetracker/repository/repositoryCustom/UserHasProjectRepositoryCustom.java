package rs.dzoks.timetracker.repository.repositoryCustom;

import rs.dzoks.timetracker.model.modelCustom.UserHasProjectUserTimesheet;

import java.util.List;

public interface UserHasProjectRepositoryCustom {

    List<UserHasProjectUserTimesheet> getAllByProject(Integer projectId);
}
