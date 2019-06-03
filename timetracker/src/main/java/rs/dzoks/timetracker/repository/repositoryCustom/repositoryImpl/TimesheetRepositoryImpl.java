package rs.dzoks.timetracker.repository.repositoryCustom.repositoryImpl;

import rs.dzoks.timetracker.model.Timesheet;
import rs.dzoks.timetracker.model.modelCustom.TimesheetProject;
import rs.dzoks.timetracker.repository.repositoryCustom.TimesheetRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TimesheetRepositoryImpl implements TimesheetRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SQL_GET_BY_USER="select t.*,uhp.id as user_has_project_id,p.name as project_name from timesheet t join user_has_project uhp on t.user_has_project_id = uhp.id join project p on uhp.project_id = p.id where t.active=1 and uhp.active=1 and uhp.user_id=?";

    private static final String SQL_GET_BY_USER_AND_PROJECT="select t.* from timesheet t join user_has_project uhp on t.user_has_project_id = uhp.id where t.active=1 and uhp.active=1 and uhp.user_id=? and uhp.project_id=?";

    @Override
    public List<TimesheetProject> getByUser(Integer userId) {
        return entityManager.createNativeQuery(SQL_GET_BY_USER,"TimesheetProjectMapping").setParameter(1,userId).getResultList();
    }

    public List<Timesheet> getByUserAndProject(Integer userId,Integer projectId){
        return entityManager.createNativeQuery(SQL_GET_BY_USER_AND_PROJECT,Timesheet.class).setParameter(1,userId).setParameter(2,projectId).getResultList();

    }

}
