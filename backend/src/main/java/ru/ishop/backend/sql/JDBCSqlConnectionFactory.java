package ru.ishop.backend.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Aleksandr Smirnov.
 */
public class JDBCSqlConnectionFactory implements SqlConnectionFactory {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/shop?" +
                "user=shop_user&password=123456");
    }
}
