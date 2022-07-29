package ua.issoft.yurii.kupchyn.daos.impl;

import ua.issoft.yurii.kupchyn.daos.ConnectionPool;
import ua.issoft.yurii.kupchyn.daos.DaoException;
import ua.issoft.yurii.kupchyn.daos.ProductDao;
import ua.issoft.yurii.kupchyn.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoDb implements ProductDao {
    private final ConnectionPool connectionPool;

    private static final String CATEGORY_ID_COLUMN = "categoryId";
    private static final String PRODUCT_NAME_COLUMN = "productName";
    private static final String PRODUCT_PRODUCER_COLUMN = "productProducer";
    private static final String PRODUCT_PRICE_COLUMN = "productPrice";
    private static final String PRODUCT_ID_COLUMN = "productId";
    private static final String INSERT_PRODUCTS_QUERY = "INSERT INTO " +
            "products(categoryId, productName, productProducer, productPrice) VALUES (?, ?, ?, ?);";
    private static final String SELECT_PRODUCTS_BY_CATEGORY_ID = "SELECT productId, productProducer, productName, productPrice "
            + "FROM products WHERE categoryId= ?;";

    private static final String SELECT_PRODUCT_BY_ID = "SELECT productId, categoryId, productProducer, productName, productPrice "
            + "FROM products WHERE productId= ?;";

    public ProductDaoDb(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Product> getAllByCategoryId(int categoryId) {
        List<Product> productList = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_PRODUCTS_BY_CATEGORY_ID)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt(PRODUCT_ID_COLUMN);
                    String producer = rs.getString(PRODUCT_PRODUCER_COLUMN);
                    String name = rs.getString(PRODUCT_NAME_COLUMN);
                    int price = rs.getInt(PRODUCT_PRICE_COLUMN);

                    productList.add(new Product(id, categoryId, producer, name, price));
                }
            }

            return productList;
        } catch (SQLException e) {
            throw new DaoException("Failed to get all products by categoryId: " + categoryId, e);
        }
    }

    public void add(int categoryId, Product product) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_PRODUCTS_QUERY)) {

            ps.setInt(1, categoryId);
            ps.setString(2, product.getName());
            ps.setString(3, product.getProducer());
            ps.setInt(4, product.getPrice());

            ps.execute();

        } catch (SQLException e) {
            throw new DaoException("Failed to add product: " + product, e);
        }
    }

    @Override
    public Optional<Product> getById(int productId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            Product product = null;

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(PRODUCT_ID_COLUMN);
                    int categoryId = rs.getInt(CATEGORY_ID_COLUMN);
                    String producer = rs.getString(PRODUCT_PRODUCER_COLUMN);
                    String name = rs.getString(PRODUCT_NAME_COLUMN);
                    int price = rs.getInt(PRODUCT_PRICE_COLUMN);

                    product = new Product(id, categoryId, producer, name, price);
                }
            }

            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DaoException("Failed to get product by productId: " + productId, e);
        }
    }
}