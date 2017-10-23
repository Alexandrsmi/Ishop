package ru.ishop.backend.services.user;

import ru.ishop.backend.data.Id;

/**
 * Класс пользователя.
 * @author Aleksandr Smirnov.
 */
public class User extends Id {
    /**
     * Поле имени пользоветля.
     */
    private String firstName;
    /**
     * Поле фамилии пользователя.
     */
    private String lastName;
    /**
     * Поле пароля пользователя.
     */
    private String password;
    /**
     * Поле email пользователя.
     */
    private String email;
    /**
     * Поле статуса пользователя.
     */
    private UserState state;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }
}
