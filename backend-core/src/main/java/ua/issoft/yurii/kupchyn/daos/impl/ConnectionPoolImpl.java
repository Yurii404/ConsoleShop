package ua.issoft.yurii.kupchyn.daos.impl;

import ua.issoft.yurii.kupchyn.daos.ConnectionPool;
import ua.issoft.yurii.kupchyn.daos.DaoException;
import ua.issoft.yurii.kupchyn.services.InterfaceFinder;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectionPoolImpl implements ConnectionPool {
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections;
    private static int INITIAL_POOL_SIZE = 10;

    private static ConnectionPoolImpl connectionPoolImpl = null;

    private ConnectionPoolImpl(List<Connection> connectionPool, List<Connection> usedConnections) {
        this.connectionPool = connectionPool;
        this.usedConnections = usedConnections;
    }

    private static ConnectionPoolImpl create() {
        List<Connection> pool = Collections.synchronizedList(new ArrayList<>(INITIAL_POOL_SIZE));
        List<Connection> usedConnection = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(ConnectionFactoryImpl.getInstance().getConnection());
        }

        return new ConnectionPoolImpl(pool, usedConnection);
    }


    @Override
    public synchronized Connection getConnection() {
        Connection connection;

        if (connectionPool.size() < 1) {
            connection = ConnectionFactoryImpl.getInstance().getConnection();
        } else {
            connection = connectionPool.remove(connectionPool.size() - 1);
        }

        usedConnections.add(connection);

        try {
            return (Connection) Proxy.newProxyInstance(
                    Connection.class.getClassLoader(),
                    InterfaceFinder.getAllInterfaces(Connection.class),
                    new ConnectionInvocationHandler(connection));
        } catch (SQLException e) {
            throw new DaoException("Failed to return connection proxy", e);
        }
    }

    @Override
    public synchronized boolean releaseConnection(Connection connection) {
        if (connectionPool.size() != INITIAL_POOL_SIZE) {
            connectionPool.add(connection);
        }

        return usedConnections.remove(connection);
    }

    public static ConnectionPoolImpl getConnectionPool() {
        if (connectionPoolImpl == null) {
            connectionPoolImpl = create();
        }
        return connectionPoolImpl;
    }


    public synchronized int getSize() {
        return connectionPool.size() + usedConnections.size();
    }


}
