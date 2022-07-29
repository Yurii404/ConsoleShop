package ua.issoft.yurii.kupchyn.daos.impl;

import org.junit.Before;
import org.junit.Test;
import ua.issoft.yurii.kupchyn.daos.DaoException;
import ua.issoft.yurii.kupchyn.entities.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CategoryDaoDbTest {
    private CategoryDaoDb categoryDaoDb;

    @Before
    public void setUp() {
        categoryDaoDb = new CategoryDaoDb(ConnectionPoolImpl.getConnectionPool());
    }

    @Test
    public void should_returnListOfCategories() {
        List<Category> categoryList = categoryDaoDb.getAll();
        boolean result = categoryList.isEmpty();

        assertFalse(result);
    }

    @Test
    public void should_returnCategoryByName() {
        boolean result = false;
        Optional<Category> category = categoryDaoDb.getByName("Phones");
        if (category.isPresent()) {
            result = category.get().getName().equals("Phones");
        }

        assertTrue(result);
    }

    @Test
    public void should_returnCategoryById() {
        boolean result = false;
        Optional<Category> category = categoryDaoDb.getById(1);
        if (category.isPresent()) {
            result = category.get().getId() == 1;
        }

        assertTrue(result);
    }

    @Test(expected = DaoException.class)
    public void should_throwDaoExceptionFailedToExecuteSql() throws SQLException {
        Connection connection = ConnectionPoolImpl.getConnectionPool().getConnection();
        connection.createStatement().execute("ALTER TABLE categories ALTER COLUMN categoryName RENAME TO wrongName");

        categoryDaoDb.getByName("Phones");
    }

}