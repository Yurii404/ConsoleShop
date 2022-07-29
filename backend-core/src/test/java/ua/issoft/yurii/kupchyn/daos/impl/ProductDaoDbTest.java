package ua.issoft.yurii.kupchyn.daos.impl;

import org.junit.Before;
import org.junit.Test;
import ua.issoft.yurii.kupchyn.daos.DaoException;
import ua.issoft.yurii.kupchyn.entities.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductDaoDbTest {
    private ProductDaoDb productDaoDb;

    @Before
    public void setUp() {
        productDaoDb = new ProductDaoDb(ConnectionPoolImpl.getConnectionPool());
    }

    @Test
    public void should_AddProduct() {
        Product product = new Product(2, "Test", "Test", 500);

        productDaoDb.add(2, product);
    }

    @Test
    public void should_returnListOfProductsByCategoryId() {
        List<Product> productList = productDaoDb.getAllByCategoryId(1);
        boolean result = productList.isEmpty();

        assertFalse(result);
    }

    @Test(expected = DaoException.class)
    public void should_throwDaoExceptionFailedToExecuteSql() throws SQLException {
        Connection connection = ConnectionPoolImpl.getConnectionPool().getConnection();
        connection.createStatement().execute("ALTER TABLE products ALTER COLUMN productName RENAME TO wrongName");

        Product product = new Product(2, "Test", "Test", 500);
        productDaoDb.add(2, product);
    }


    @Test
    public void should_returnProductById() {
        boolean result = false;
        Optional<Product> product = productDaoDb.getById(1);
        if (product.isPresent()) {
            result = product.get().getId() == 1;
        }

        assertTrue(result);
    }


}