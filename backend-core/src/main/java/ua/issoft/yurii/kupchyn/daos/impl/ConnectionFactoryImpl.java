package ua.issoft.yurii.kupchyn.daos.impl;

import ua.issoft.yurii.kupchyn.daos.ConnectionFactory;
import ua.issoft.yurii.kupchyn.daos.ConnectionFactoryException;
import ua.issoft.yurii.kupchyn.services.PropertyUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {
    private String driverClassName;
    private String connectionUrl;
    private String dbUser;
    private String dbPwd;

    private static ConnectionFactoryImpl connectionBuilderImpl = null;

    private ConnectionFactoryImpl() {
        try {
            PropertyUtil propertyUtil = new PropertyUtil();

            driverClassName = propertyUtil.getPropertyValue("db.driver");
            connectionUrl = propertyUtil.getPropertyValue("db.connectionUrl");
            dbUser = propertyUtil.getPropertyValue("db.user");
            dbPwd = propertyUtil.getPropertyValue("db.password");

            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new ConnectionFactoryException("Failed to load driver: " + driverClassName, e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
        } catch (SQLException e) {
            throw new ConnectionFactoryException("Failed connection", e);
        }
    }

    public static ConnectionFactoryImpl getInstance() {
        if (connectionBuilderImpl == null) {
            connectionBuilderImpl = new ConnectionFactoryImpl();
        }
        return connectionBuilderImpl;
    }
}