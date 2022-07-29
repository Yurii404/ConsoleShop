package ua.issoft.yurii.kupchyn.daos.impl;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConnectionPoolImplTest {

    @Test
    public void should_returnConnectionPoolInstance() {
        ConnectionPoolImpl connectionPool = ConnectionPoolImpl.getConnectionPool();

        assertEquals(ConnectionPoolImpl.class, connectionPool.getClass());
    }

    @Test
    public void should_returnConnection() {
        Connection connection = ConnectionPoolImpl.getConnectionPool().getConnection();

        assertNotNull(connection);
    }

    @Test
    public void testReleaseConnection() {
        ConnectionPoolImpl connectionPool = ConnectionPoolImpl.getConnectionPool();
        Connection connection = connectionPool.getConnection();


        assertTrue(connectionPool.releaseConnection(connection));
    }

}