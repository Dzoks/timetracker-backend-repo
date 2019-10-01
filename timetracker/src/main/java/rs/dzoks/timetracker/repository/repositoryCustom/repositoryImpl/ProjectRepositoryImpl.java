package rs.dzoks.timetracker.repository.repositoryCustom.repositoryImpl;

import rs.dzoks.timetracker.model.Project;
import rs.dzoks.timetracker.model.modelCustom.ProjectUserHasProject;
import rs.dzoks.timetracker.repository.repositoryCustom.ProjectRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final static String SQL_GET_FOR_USER="select p.*,uhp.id as user_has_project_id,(SELECT SUM(hours) FROM timesheet t JOIN user_has_project u ON t.user_has_project_id=u.id WHERE u.user_id=uhp.user_id and u.project_id=p.id) AS total_hours,(SELECT SUM(turnover) FROM timesheet t JOIN user_has_project u ON t.user_has_project_id=u.id WHERE u.user_id=uhp.user_id and u.project_id=p.id) AS total_amount from project p join user_has_project uhp on p.id = uhp.project_id where p.active=1 and uhp.active=1 and uhp.user_id=?;";
    private final static String SQL_GET_FOR_PROJECT_MANAGER="select p.*,uhp.id as user_has_project_id," +
            "if (p.project_manager_id=?,(SELECT SUM(hours) FROM timesheet t JOIN user_has_project u ON t.user_has_project_id=u.id WHERE u.project_id=p.id)," +
            "(SELECT SUM(hours) FROM timesheet t JOIN user_has_project u ON t.user_has_project_id=u.id WHERE u.user_id=uhp.user_id and u.project_id=p.id)) AS total_hours," +
            "if (p.project_manager_id=?,(SELECT SUM(turnover) FROM timesheet t JOIN user_has_project u ON t.user_has_project_id=u.id WHERE u.project_id=p.id)," +
            "(SELECT SUM(turnover) FROM timesheet t JOIN user_has_project u ON t.user_has_project_id=u.id WHERE u.user_id=uhp.user_id and u.project_id=p.id)) AS total_amount" +
            " from project p join user_has_project uhp on p.id = uhp.project_id where p.active=1 and uhp.active=1 and uhp.user_id=?";


    @Override
    public List<ProjectUserHasProject> getProjectsForUser(Integer userId) {
        return entityManager.createNativeQuery(SQL_GET_FOR_USER,"ProjectUserHasProjectMapping").setParameter(1,userId).getResultList();
    }

    @Override
    public List<ProjectUserHasProject> getProjectsForProjectManager(Integer userId) {
        return entityManager.createNativeQuery(SQL_GET_FOR_PROJECT_MANAGER,"ProjectUserHasProjectMapping").setParameter(1,userId).setParameter(2,userId).setParameter(3,userId).getResultList();
    }
}
