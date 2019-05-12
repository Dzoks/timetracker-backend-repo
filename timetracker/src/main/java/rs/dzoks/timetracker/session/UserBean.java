package rs.dzoks.timetracker.session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import rs.dzoks.timetracker.model.User;

@Component
@Scope("session")
public class UserBean {

    private User user;
    private boolean authorized=false;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }
}
