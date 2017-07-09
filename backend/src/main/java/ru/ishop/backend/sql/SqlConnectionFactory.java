package ru.ishop.backend.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Aleksandr Smirnov.
 */
public interface SqlConnectionFactory {
    Connection getConnection() throws SQLException;
}
