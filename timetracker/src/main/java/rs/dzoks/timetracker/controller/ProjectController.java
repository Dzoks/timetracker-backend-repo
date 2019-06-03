package rs.dzoks.timetracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.common.BadRequestException;
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
        return projectRepository.getProjectsForUser(userBean.getUser().getId());
    }


    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ProjectUserHasProject insert(@RequestBody Project project) throws BadRequestException {
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
       // entityManager.refresh(project);
        return puhp;
    }

    @PutMapping
    public Boolean update(@RequestBody Project project){
        project=projectRepository.getProjectByIdAndActive(project.getId(),(byte)1);
        if (project==null || !project.getProjectManagerId().equals(userBean.getUser().getId()))
            return false;
        return projectRepository.saveAndFlush(project)!=null;

    }

    @DeleteMapping("/{projectId}")
    public Boolean deleteProject(@PathVariable Integer projectId){
        Project project=projectRepository.getProjectByIdAndActive(projectId,(byte)1);
        if (project==null)
            return false;
        project.setActive((byte)0);
        return projectRepository.saveAndFlush(project)!=null;
    }

}
