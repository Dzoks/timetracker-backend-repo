package rs.dzoks.timetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.dzoks.timetracker.model.modelCustom.UserHasProjectUserTimesheet;
import rs.dzoks.timetracker.repository.UserHasProjectRepository;

import java.util.List;

@RestController
@RequestMapping("hub/userHasProject")
@Scope("session")
public class UserHasProjectController {

    private final UserHasProjectRepository userHasProjectRepository;

    public UserHasProjectController(UserHasProjectRepository userHasProjectRepository) {
        this.userHasProjectRepository = userHasProjectRepository;
    }

    @GetMapping("/{projectId}")
    List<UserHasProjectUserTimesheet> getByProject(@PathVariable Integer projectId){
        return userHasProjectRepository.getAllByProject(projectId);
    }
}
