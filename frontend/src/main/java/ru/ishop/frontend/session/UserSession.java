package ru.ishop.frontend.session;

import ru.ishop.backend.services.user.User;
import ru.ishop.backend.services.user.UserState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Aleksandr Smirnov.
 */
public class UserSession {
    private static final String REQUEST_SESSION = "user.session";
    private User user;

    public static UserSession getUserSession(HttpServletRequest request) {
        UserSession userSession = (UserSession) request.getSession().getAttribute(REQUEST_SESSION);
        if (userSession == null) {
            userSession = new UserSession();
            request.getSession().setAttribute(REQUEST_SESSION, userSession);
        }
        return userSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isGuest() {
        return user == null;
    }

    public boolean isAdmin() {
        return !isGuest() && user.getState() == UserState.ADMIN;
    }
}
