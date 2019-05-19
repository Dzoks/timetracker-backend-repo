package rs.dzoks.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dzoks.timetracker.model.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup,Integer> {
}
