package ua.issoft.yurii.kupchyn.daos.impl;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionInvocationHandler implements InvocationHandler {
    private final Connection original;
    private final ConnectionPoolImpl connectionPool;

    public ConnectionInvocationHandler(Connection connection) throws SQLException {
        connectionPool = ConnectionPoolImpl.getConnectionPool();
        this.original = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.getName().equals("close") ?
                    connectionPool.releaseConnection(original) :
                    method.invoke(original, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }

    }
}