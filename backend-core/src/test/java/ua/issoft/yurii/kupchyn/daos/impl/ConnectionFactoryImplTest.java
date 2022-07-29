package ua.issoft.yurii.kupchyn.daos.impl;

import org.h2.jdbc.JdbcConnection;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;

public class ConnectionFactoryImplTest {
    @Test
    public void should_returnConnectionFactoryInstance() {
        ConnectionFactoryImpl connectionFactory = ConnectionFactoryImpl.getInstance();

        assertEquals(ConnectionFactoryImpl.class, connectionFactory.getClass());
    }

    @Test
    public void should_returnConnection() {
        Connection connection = ConnectionFactoryImpl.getInstance().getConnection();

        assertEquals(JdbcConnection.class, connection.getClass());

    }


}




