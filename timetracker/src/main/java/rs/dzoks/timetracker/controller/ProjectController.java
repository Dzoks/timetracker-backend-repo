package rs.dzoks.timetracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.common.BadRequestException;
import rs.dzoks.timetracker.common.UserGroupMap;
import rs.dzoks.timetracker.model.Project;
import rs.dzoks.timetracker.repository.ProjectRepository;
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

    private final UserGroupMap userGroupMap;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, UserBean userBean, UserGroupMap userGroupMap) {
        this.projectRepository = projectRepository;
        this.userBean = userBean;
        this.userGroupMap = userGroupMap;
    }

    @GetMapping
    public List<Project> getAll(){
        int userGroupId=userBean.getUser().getUserGroupId();
       if (userGroupMap.getByKey("projectManager").equals(userGroupId))
           return projectRepository.getAllByProjectManagerIdAndActive(userBean.getUser().getId(),(byte)1);
       else if (userGroupMap.getByKey("user").equals(userGroupId))
           return projectRepository.getProjectsForUser(userBean.getUser().getId());
       else return new ArrayList<>();
    }


    @PostMapping
    public Project insert(@RequestBody Project project) throws BadRequestException {
        project=projectRepository.saveAndFlush(project);
        if (project==null)
            throw new BadRequestException("insertFail");
        entityManager.refresh(project);
        return project;
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
