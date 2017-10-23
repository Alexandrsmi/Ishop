package ru.ishop.backend.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Интерфейс подключения к базе данных.
 * @author Aleksandr Smirnov.
 */
public interface SqlConnectionFactory {
    Connection getConnection() throws SQLException;
}
