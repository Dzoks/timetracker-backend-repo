package rs.dzoks.timetracker.repository.repositoryCustom.repositoryImpl;

import rs.dzoks.timetracker.model.Timesheet;
import rs.dzoks.timetracker.model.modelCustom.TimesheetProject;
import rs.dzoks.timetracker.model.modelOther.LineChartModel;
import rs.dzoks.timetracker.model.modelOther.PieChartModel;
import rs.dzoks.timetracker.repository.repositoryCustom.TimesheetRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TimesheetRepositoryImpl implements TimesheetRepositoryCustom {

    private static final String SQL_GET_TURNOVER_FOR_PROJECT = "SELECT t.date,SUM(turnover) AS `value` FROM timesheet t JOIN user_has_project uhp ON t.user_has_project_id=uhp.id WHERE uhp.project_id=? GROUP BY t.date;";
    private static final String SQL_GET_HOURS_FOR_PROJECT = "SELECT t.date,cast(SUM(hours) as decimal(10,2)) AS `value` FROM timesheet t JOIN user_has_project uhp ON t.user_has_project_id=uhp.id WHERE uhp.project_id=? GROUP BY t.date;";
    private static final String SQL_GET_HOURS_FOR_USER = "SELECT t.date,SUM(turnover) AS `value` FROM timesheet t JOIN user_has_project uhp ON t.user_has_project_id=uhp.id WHERE uhp.user_id=? GROUP BY t.date;";
    private static final String SQL_GET_TURNOVER_FOR_USER = "SELECT t.date,cast(SUM(hours) as decimal(10,2)) AS `value` FROM timesheet t JOIN user_has_project uhp ON t.user_has_project_id=uhp.id WHERE uhp.user_id=? GROUP BY t.date;";;

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SQL_GET_BY_USER="select t.*,uhp.id as user_has_project_id,p.name as project_name from timesheet t join user_has_project uhp on t.user_has_project_id = uhp.id join project p on uhp.project_id = p.id where t.active=1 and uhp.active=1 and uhp.user_id=?";

    private static final String SQL_GET_BY_USER_AND_PROJECT="select t.* from timesheet t join user_has_project uhp on t.user_has_project_id = uhp.id where t.active=1 and uhp.active=1 and uhp.user_id=? and uhp.project_id=?";

    private static final String SQL_GET_TURNOVER_FOR_PROJECT_BY_USERS="SELECT u.id,concat(u.first_name,' ',u.last_name,' (',u.email,')') AS `text`,SUM(turnover) AS `value` FROM timesheet t JOIN user_has_project uhp  ON t.user_has_project_id=uhp.id JOIN user u ON uhp.user_id=u.id WHERE uhp.project_id=? GROUP BY uhp.user_id;";
    private static final String SQL_GET_HOURS_FOR_PROJECT_BY_USERS="SELECT u.id,concat(u.first_name,' ',u.last_name,' (',u.email,')') AS `text`,cast(SUM(hours) as decimal(10,2)) AS `value` FROM timesheet t JOIN user_has_project uhp  ON t.user_has_project_id=uhp.id JOIN user u ON uhp.user_id=u.id WHERE uhp.project_id=? GROUP BY uhp.user_id;";
    private static final String SQL_GET_TURNOVER_FOR_USER_BY_PROJECTS = "SELECT p.id,p.name AS `text`,SUM(turnover) AS `value` FROM timesheet t JOIN user_has_project uhp  ON t.user_has_project_id=uhp.id JOIN project p ON uhp.project_id=p.id WHERE uhp.user_id=? GROUP BY uhp.project_id;";
    private static final String SQL_GET_HOURS_FOR_USER_BY_PROJECTS ="SELECT p.id,p.name AS `text`,cast(SUM(hours) as decimal(10,2)) AS `value` FROM timesheet t JOIN user_has_project uhp  ON t.user_has_project_id=uhp.id JOIN project p on uhp.project_id = p.id WHERE uhp.user_id=? GROUP BY uhp.project_id;";
    @Override
    public List<TimesheetProject> getByUser(Integer userId) {
        return entityManager.createNativeQuery(SQL_GET_BY_USER,"TimesheetProjectMapping").setParameter(1,userId).getResultList();
    }

    public List<Timesheet> getByUserAndProject(Integer userId,Integer projectId){
        return entityManager.createNativeQuery(SQL_GET_BY_USER_AND_PROJECT,Timesheet.class).setParameter(1,userId).setParameter(2,projectId).getResultList();

    }

    @Override
    public List<PieChartModel> getTurnoverForProjectByUsers(Integer projectId) {
        return entityManager.createNativeQuery(SQL_GET_TURNOVER_FOR_PROJECT_BY_USERS,"PieChartMapping").setParameter(1,projectId).getResultList();
    }

    @Override
    public List<PieChartModel> getHoursForProjectByUsers(Integer projectId) {
        return entityManager.createNativeQuery(SQL_GET_HOURS_FOR_PROJECT_BY_USERS,"PieChartMapping").setParameter(1,projectId).getResultList();
    }

    @Override
    public List<LineChartModel> getTurnoverForProject(Integer projectId) {
        return entityManager.createNativeQuery(SQL_GET_TURNOVER_FOR_PROJECT,"LineChartMapping").setParameter(1,projectId).getResultList();
    }

    @Override
    public List<LineChartModel> getHoursForProject(Integer projectId) {
        return entityManager.createNativeQuery(SQL_GET_HOURS_FOR_PROJECT,"LineChartMapping").setParameter(1,projectId).getResultList();
    }

    @Override
    public List<LineChartModel> getHoursForUser(Integer userId) {
        return entityManager.createNativeQuery(SQL_GET_HOURS_FOR_USER,"LineChartMapping").setParameter(1,userId).getResultList();

    }

    @Override
    public List<LineChartModel> getTurnoverForUser(Integer userId) {
        return entityManager.createNativeQuery(SQL_GET_TURNOVER_FOR_USER,"LineChartMapping").setParameter(1,userId).getResultList();

    }

    @Override
    public List<PieChartModel> getTurnoverForUserByProjects(Integer userId) {
        return entityManager.createNativeQuery(SQL_GET_TURNOVER_FOR_USER_BY_PROJECTS,"PieChartMapping").setParameter(1,userId).getResultList();
    }

    @Override
    public List<PieChartModel> getHoursForUserByProjects(Integer userId) {
        return entityManager.createNativeQuery(SQL_GET_HOURS_FOR_USER_BY_PROJECTS,"PieChartMapping").setParameter(1,userId).getResultList();
    }


}
