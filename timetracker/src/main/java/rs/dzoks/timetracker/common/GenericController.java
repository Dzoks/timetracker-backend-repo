package rs.dzoks.timetracker.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import rs.dzoks.timetracker.session.UserBean;

public class GenericController {

    protected final UserBean userBean;

    protected final UserGroupMap userGroupMap;

    public GenericController(UserBean userBean, UserGroupMap userGroupMap) {
        this.userBean = userBean;
        this.userGroupMap = userGroupMap;
    }
}
