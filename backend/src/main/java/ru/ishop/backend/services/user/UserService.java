package ru.ishop.backend.services.user;

/**
 * @author Aleksandr Smirnov.
 */
public interface UserService {
    boolean createUser(User user);
    boolean existsEmail(String email);

    User getUser(String email, String password);
    User getUser(long userId);
    boolean updateUser(User user);
}
