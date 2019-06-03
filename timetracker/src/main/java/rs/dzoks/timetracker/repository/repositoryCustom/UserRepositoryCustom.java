package rs.dzoks.timetracker.repository.repositoryCustom;

import rs.dzoks.timetracker.model.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> getAvailableFor(Integer projectId)
            ;
}
