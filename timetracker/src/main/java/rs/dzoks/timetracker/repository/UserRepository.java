package rs.dzoks.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dzoks.timetracker.model.User;
import rs.dzoks.timetracker.repository.repositoryCustom.UserRepositoryCustom;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer>, UserRepositoryCustom {

    List<User> getAllByActive(Byte active);

    Boolean existsUserByEmailAndActive(String email,Byte active);

    User getUserByEmailAndActive(String email,Byte active);
    User getUserByIdAndActive(Integer id,Byte active);
    User getUserByIdAndPasswordAndActive(Integer id,String password,Byte active);
    User getUserByEmailAndPasswordAndActive(String email,String password,Byte active);
}
