package ru.ishop.backend.services;

import ru.ishop.backend.sql.SqlConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Aleksandr Smirnov.
 */
public abstract class AbstractSqlService {
    private SqlConnectionFactory factory;

    public AbstractSqlService(SqlConnectionFactory factory) {
        this.factory = factory;
    }

    protected Connection getConnection() throws SQLException {
        return factory.getConnection();
    }

    public long getAutoGeneratedKey(Statement statement) throws SQLException{
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Auto generated id not found");
            }
        }
    }

}