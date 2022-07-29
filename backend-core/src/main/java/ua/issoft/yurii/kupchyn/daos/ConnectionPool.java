package ua.issoft.yurii.kupchyn.daos;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();

    boolean releaseConnection(Connection connection);
}
