package ru.ishop.backend.services.user;

/**
 * Класс пользовательских сервисов.
 * @author Aleksandr Smirnov.
 */
public interface UserService {
    /**
     * Метод создания пользовтеля.
     * @param user - объект пользователя.
     * @return - true - при успешном создании.
     */
    boolean createUser(User user);

    /**
     * Метод проверяет уникальность email.
     * Не позовляет создавать двух пользователей с одинаковыми email.
     * @param email - электронный почтовый адрес.
     * @return - true - true если email уникальный.
     */
    boolean existsEmail(String email);

    /**
     * Метод получения пользователя по email и паролю.
     * @param email - электронный почтовый адрес.
     * @param password - пароль.
     * @return - пользователя.
     */
    User getUser(String email, String password);

    /**
     * Метод получения пользователя по id.
     * @param userId - id - пользователя.
     * @return - пользователя.
     */
    User getUser(long userId);

    /**
     * Метод изменения пользователя.
     * @param user - пользователь.
     * @return - true - при успешном изменении.
     */
    boolean updateUser(User user);
}
