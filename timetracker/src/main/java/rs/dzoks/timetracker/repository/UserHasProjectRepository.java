package rs.dzoks.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dzoks.timetracker.model.UserHasProject;
import rs.dzoks.timetracker.repository.repositoryCustom.UserHasProjectRepositoryCustom;

public interface UserHasProjectRepository extends JpaRepository<UserHasProject,Integer>, UserHasProjectRepositoryCustom {
    UserHasProject getUserHasProjectByIdAndActive(Integer id,Byte active);

}
