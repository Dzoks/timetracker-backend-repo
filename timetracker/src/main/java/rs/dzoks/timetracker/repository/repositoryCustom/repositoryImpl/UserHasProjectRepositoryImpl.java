package rs.dzoks.timetracker.repository.repositoryCustom.repositoryImpl;

import rs.dzoks.timetracker.model.modelCustom.UserHasProjectUserTimesheet;
import rs.dzoks.timetracker.repository.repositoryCustom.UserHasProjectRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserHasProjectRepositoryImpl implements UserHasProjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final String SQL_GET_ALL_CUSTOM="SELECT uhp.id,user_id,project_id,first_name,last_name,hour_rate,SUM(hours) AS total_hours,SUM(turnover) AS total_turnover,uhp.blocked" +
            " FROM user_has_project uhp JOIN user u ON uhp.user_id=u.id left JOIN timesheet t ON uhp.id=t.user_has_project_id where uhp.active=1 and u.active=1 and (t.active is null or t.active=1) and uhp.project_id=? GROUP BY uhp.id";


    @Override
    public List<UserHasProjectUserTimesheet> getAllByProject(Integer projectId) {
        return entityManager.createNativeQuery(SQL_GET_ALL_CUSTOM,"UserHasProjectUserTimesheetMapping").setParameter(1,projectId).getResultList();
    }
}
