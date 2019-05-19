package rs.dzoks.timetracker.repository.repositoryCustom.repositoryImpl;

import rs.dzoks.timetracker.model.Project;
import rs.dzoks.timetracker.repository.repositoryCustom.ProjectRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final static String SQL_GET_FOR_USER="select p.* from project p join user_has_project uhp on p.id = uhp.project_id where p.active=1 and uhp.active=1 and uhp.user_id=?;";

    @Override
    public List<Project> getProjectsForUser(Integer userId) {
        return entityManager.createNativeQuery(SQL_GET_FOR_USER).setParameter(1,userId).getResultList();
    }
}
