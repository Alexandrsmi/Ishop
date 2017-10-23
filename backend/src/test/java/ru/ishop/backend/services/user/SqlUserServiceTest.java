package ru.ishop.backend.services.user;

import org.junit.Test;
import ru.ishop.backend.services.AbstractSqlServiceTest;

import static org.junit.Assert.*;

/**
 * @author Aleksandr Smirnov.
 */
public class SqlUserServiceTest extends AbstractSqlServiceTest {
    /**
     * Тест метода создания пользователя.
     */
    @Test
    public void createUser() {
        User user = createNewUser();
        User dbUser = getUserService().getUser(user.getEmail(), user.getPassword());
        assertNotNull(dbUser);
        assertEquals(user.getId(), dbUser.getId());
        assertEquals(user.getFirstName(), dbUser.getFirstName());
        assertEquals(user.getLastName(), dbUser.getLastName());
        assertEquals(user.getEmail(), dbUser.getEmail());
        assertEquals(user.getPassword(), dbUser.getPassword());
        assertEquals(UserState.CUSTOMER, dbUser.getState());
    }

    @Test
    public void getNotExistingUserByEmailPassword() {
        User user = createNewUser();
        User dbUser = getUserService().getUser(user.getEmail(), "wrongPassword");
        assertNull(dbUser);
        dbUser = getUserService().getUser("wrongEmail", user.getPassword());
        assertNull(dbUser);
    }

    /**
     * Тест метода получения пользователя по Id.
     */
    @Test
    public void getUserById() {
        User user = createNewUser();
        User dbUser = getUserService().getUser(user.getId());
        assertNotNull(dbUser);
        assertEquals(user.getId(), dbUser.getId());
        assertEquals(user.getFirstName(), dbUser.getFirstName());
        assertEquals(user.getLastName(), dbUser.getLastName());
        assertEquals(user.getEmail(), dbUser.getEmail());
        assertEquals(user.getPassword(), dbUser.getPassword());
        assertEquals(UserState.CUSTOMER, dbUser.getState());
    }

    @Test
    public void getNotExistingUserById() {
        User user = createNewUser();
        User dbUser = getUserService().getUser(user.getId()+1);
        assertNull(dbUser);
    }

    @Test
    public void updateUser() {
        User user = createNewUser();
        user.setFirstName("Vasya-1");
        user.setLastName("Petrov-1");
        user.setEmail(salted("Test@email.ru"));
        user.setPassword("321");
        getUserService().updateUser(user);
        User dbUser = getUserService().getUser(user.getId());
        assertNotNull(dbUser);
        assertEquals(user.getId(), dbUser.getId());
        assertEquals(user.getFirstName(), dbUser.getFirstName());
        assertEquals(user.getLastName(), dbUser.getLastName());
        assertEquals(user.getEmail(), dbUser.getEmail());
        assertEquals(user.getPassword(), dbUser.getPassword());
        assertEquals(UserState.CUSTOMER, dbUser.getState());
    }

    @Test
    public void existsEmail(){
        User user = createNewUser();
        boolean exists = getUserService().existsEmail(user.getEmail());
        assertTrue(exists);
        exists = getUserService().existsEmail("wrongEmail");
        assertFalse(exists);
    }

    /**
     * Метод создает пользователя.
     * @return user - пользователь.
     */
    private User createNewUser() {
        User user = new User();
        user.setFirstName("Vasya");
        user.setLastName("Petrov");
        user.setEmail(salted("Petr@email.ru"));
        user.setPassword("123");
        boolean result = getUserService().createUser(user);
        assertTrue(result);
        assertNotNull(user.getId());
        return user;
    }
}
