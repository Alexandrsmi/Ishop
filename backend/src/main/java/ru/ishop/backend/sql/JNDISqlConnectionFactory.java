package ru.ishop.backend.sql;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Aleksandr Smirnov.
 */
public class JNDISqlConnectionFactory implements SqlConnectionFactory {
    private String DATASOURCE_CONTEXT = "java:comp/env/jdbc/SHOP_DB";

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Context initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
            return datasource.getConnection();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
