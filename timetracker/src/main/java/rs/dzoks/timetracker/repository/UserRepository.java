package rs.dzoks.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dzoks.timetracker.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> getAllByActive(Byte active);

    User getUserByEmailAndPasswordAndActive(String email,String password,Byte active);
}
