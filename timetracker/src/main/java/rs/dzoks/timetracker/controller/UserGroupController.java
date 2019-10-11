package rs.dzoks.timetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.common.ForbiddenException;
import rs.dzoks.timetracker.model.UserGroup;
import rs.dzoks.timetracker.repository.UserGroupRepository;

import java.util.List;

@RestController
@RequestMapping("hub/userGroup")
@Scope("session")
public class UserGroupController {



    private final UserGroupRepository userGroupRepository;

    public UserGroupController(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    @GetMapping
    public List<UserGroup> getAll(){
        return userGroupRepository.findAll();
    }
}
