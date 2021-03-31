package org.example.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionFactoryImpl implements ConnectionFactory {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.user.name}")
    private String jdbcUserName;

    @Value("${jdbc.user.password}")
    private String jdbcUserPassword;

    @Override
    public Connection createConnection() {
        try {
            return DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcUserPassword);
        }
        catch (SQLException e) {
            throw new RuntimeException("Unable to connect DB");
        }
    }
}
