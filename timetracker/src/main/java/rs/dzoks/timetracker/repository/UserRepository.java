package rs.dzoks.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dzoks.timetracker.model.User;
import rs.dzoks.timetracker.repository.repositoryCustom.UserRepositoryCustom;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer>, UserRepositoryCustom {

    List<User> getAllByActive(Byte active);

    User getUserByEmailAndPasswordAndActive(String email,String password,Byte active);
}
