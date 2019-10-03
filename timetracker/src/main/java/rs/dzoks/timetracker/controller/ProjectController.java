package rs.dzoks.timetracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.common.BadRequestException;
import rs.dzoks.timetracker.common.ForbiddenException;
import rs.dzoks.timetracker.common.UserGroupMap;
import rs.dzoks.timetracker.model.Project;
import rs.dzoks.timetracker.model.UserGroup;
import rs.dzoks.timetracker.model.UserHasProject;
import rs.dzoks.timetracker.model.modelCustom.ProjectUserHasProject;
import rs.dzoks.timetracker.repository.ProjectRepository;
import rs.dzoks.timetracker.repository.UserHasProjectRepository;
import rs.dzoks.timetracker.session.UserBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("hub/project")
@Scope("session")
public class ProjectController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    String handleException(BadRequestException e) {
        return e.getMessage();
    }
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody
    String handleException(ForbiddenException e) {
        return e.getMessage();
    }
    @PersistenceContext
    private EntityManager entityManager;

    private final UserBean userBean;

    private final ProjectRepository projectRepository;

    private final UserHasProjectRepository userHasProjectRepository;

    private final UserGroupMap userGroupMap;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, UserBean userBean, UserGroupMap userGroupMap, UserHasProjectRepository userHasProjectRepository) {
        this.projectRepository = projectRepository;
        this.userBean = userBean;
        this.userGroupMap = userGroupMap;
        this.userHasProjectRepository = userHasProjectRepository;
    }

    @GetMapping
    public List<ProjectUserHasProject> getAll(){
        if (userGroupMap.getByKey("projectManager").equals(userBean.getUser().getUserGroupId()))
            return projectRepository.getProjectsForProjectManager(userBean.getUser().getId());
        return projectRepository.getProjectsForUser(userBean.getUser().getId());
    }


    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ProjectUserHasProject insert(@RequestBody Project project) throws BadRequestException, ForbiddenException {
        if (!userBean.getUser().getUserGroupId().equals(userGroupMap.getByKey("projectManager")))
            throw new ForbiddenException("Unauthorized");
        project.setProjectManagerId(userBean.getUser().getId());
        project=projectRepository.saveAndFlush(project);
        if (project==null)
            throw new BadRequestException("insertFail");
        UserHasProject uhp=new UserHasProject();
        uhp.setHourRate(project.getHourRate());
        uhp.setProjectId(project.getId());
        uhp.setUserId(userBean.getUser().getId());
        uhp=userHasProjectRepository.saveAndFlush(uhp);
        if (uhp==null)
            throw new BadRequestException("insertFail");
        ProjectUserHasProject puhp=new ProjectUserHasProject(project,uhp.getId());
        puhp.setTotalHours(0);
        puhp.setTotalAmount(BigDecimal.ZERO);
       // entityManager.refresh(project);
        return puhp;
    }

    @PutMapping("/finish/{projectId}")
    public Boolean finish(@PathVariable Integer projectId) {
        Project project=projectRepository.getProjectByIdAndActive(projectId,(byte)1);
        if (project==null || !project.getProjectManagerId().equals(userBean.getUser().getId()))
            return false;
        project.setFinished((byte)1);
        project.setEndDate(Date.valueOf(LocalDate.now()));
        return projectRepository.saveAndFlush(project)!=null;

    }

    @PutMapping
    public Boolean update(@RequestBody Project project){

        Project projectDb=projectRepository.getProjectByIdAndActive(project.getId(),(byte)1);
        if (projectDb==null || !projectDb.getProjectManagerId().equals(userBean.getUser().getId()))
            return false;
        return projectRepository.saveAndFlush(project)!=null;

    }

    @DeleteMapping("/{projectId}")
    public Boolean deleteProject(@PathVariable Integer projectId){
        Project project=projectRepository.getProjectByIdAndActive(projectId,(byte)1);
        if (project==null || !project.getProjectManagerId().equals(userBean.getUser().getId()))
            return false;
        project.setActive((byte)0);
        return projectRepository.saveAndFlush(project)!=null;
    }



}
