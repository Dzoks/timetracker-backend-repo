package rs.dzoks.timetracker.repository.repositoryCustom.repositoryImpl;

import rs.dzoks.timetracker.model.User;
import rs.dzoks.timetracker.repository.repositoryCustom.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final static String SQL_GET_AVAILABLE="select * from user where active=1 and id not in (select u.id from user u join user_has_project uhp on u.id=uhp.user_id where u.active=1 and uhp.active=1 and project_id=?);";

    @Override
    public List<User> getAvailableFor(Integer projectId) {
        return entityManager.createNativeQuery(SQL_GET_AVAILABLE,User.class).setParameter(1,projectId).getResultList();
    }
}
