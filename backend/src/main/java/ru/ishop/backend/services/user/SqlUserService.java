package ru.ishop.backend.services.user;

import ru.ishop.backend.services.AbstractSqlService;
import ru.ishop.backend.sql.SqlConnectionFactory;

import java.sql.*;

/**
 * Класс польвотельских сервисов.
 * @author Aleksandr Smirnov.
 */
public class SqlUserService extends AbstractSqlService implements UserService {

    public SqlUserService(SqlConnectionFactory factory) {
        super(factory);
    }

    public boolean createUser(User user) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_users_create(?,?,?,?,?)}");) {
            statement.setString("_first_name", user.getFirstName());
            statement.setString("_last_name", user.getLastName());
            statement.setString("_email", user.getEmail());
            statement.setString("_password", user.getPassword());
            statement.registerOutParameter("_user_id", Types.BIGINT);
            if (statement.executeUpdate() > 0) {
                user.setId(statement.getLong("_user_id"));
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public boolean existsEmail(String email) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_users_exists_email(?)}");) {
            statement.setString("_email", email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public User getUser(String email, String password) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_users_get_by_email(?,?)}");) {
            statement.setString("_email", email);
            statement.setString("_password", password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setState(UserState.valueOf(rs.getString(6)));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public User getUser(long userId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_users_get_by_id(?)}");) {
            statement.setString("_user_id", String.valueOf(userId));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setState(UserState.valueOf(rs.getString(6)));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public boolean updateUser(User user) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_users_update(?,?,?,?,?)}");) {
            statement.setLong("_user_id", user.getId());
            statement.setString("_first_name", user.getFirstName());
            statement.setString("_last_name", user.getLastName());
            statement.setString("_email", user.getEmail());
            statement.setString("_password", user.getPassword());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return false;
    }
}
