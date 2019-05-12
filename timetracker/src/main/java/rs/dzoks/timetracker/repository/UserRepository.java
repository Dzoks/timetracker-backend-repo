package rs.dzoks.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dzoks.timetracker.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {

    User getUserByEmailAndPasswordAndActive(String email,String password,Byte active);
}
