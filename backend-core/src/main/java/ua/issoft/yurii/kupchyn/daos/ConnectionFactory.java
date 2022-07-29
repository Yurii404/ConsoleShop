package ua.issoft.yurii.kupchyn.daos;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
