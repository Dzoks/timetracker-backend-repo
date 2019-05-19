package rs.dzoks.timetracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import rs.dzoks.timetracker.model.User;
import rs.dzoks.timetracker.repository.UserRepository;
import rs.dzoks.timetracker.session.UserBean;

import java.util.List;

@RestController
@RequestMapping("hub/user")
@Scope("request")
public class UserController {

    private final UserRepository userRepository;

    private final UserBean userBean;

    @Autowired
    public UserController(UserRepository userRepository, UserBean userBean) {
        this.userRepository = userRepository;
        this.userBean = userBean;
    }

    @GetMapping("/state")
    public User getState(){
        return userBean.getUser();
    }

    @PostMapping("/login")
    public User login(@RequestBody User loginInfo){
        User user=userRepository.getUserByEmailAndPasswordAndActive(loginInfo.getEmail(),loginInfo.getPassword(),(byte)1);
        if (user!=null){
            userBean.setAuthorized(true);
            userBean.setUser(user);
        }
        return getState();
    }

    @GetMapping
    public List<User> getAll(){
        return userRepository.getAllByActive((byte)1);
    }



}
