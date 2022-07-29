package ua.issoft.yurii.kupchyn.daos.impl;

import ua.issoft.yurii.kupchyn.daos.CategoryDao;
import ua.issoft.yurii.kupchyn.daos.ConnectionPool;
import ua.issoft.yurii.kupchyn.daos.DaoException;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoDb implements CategoryDao {

    private final ConnectionPool connectionPool;
    private final ProductDaoDb productDaoDb;
    private static final String CATEGORY_ID_COLUMN = "categoryId";
    private static final String CATEGORY_NAME_COLUMN = "categoryName";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT categoryId, categoryName FROM categories WHERE categoryId=?;";
    private static final String SELECT_CATEGORY_BY_NAME = "SELECT categoryId, categoryName FROM categories WHERE categoryName=?;";
    private static final String SELECT_CATEGORIES = "SELECT categoryId, categoryName FROM categories;";


    public CategoryDaoDb(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.productDaoDb = new ProductDaoDb(connectionPool);
    }

    @Override
    public List<Category> getAll() {
        try (Connection conn = connectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_CATEGORIES)) {

            List<Category> categoryList = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt(CATEGORY_ID_COLUMN);
                String name = rs.getString(CATEGORY_NAME_COLUMN);
                List<Product> productList = productDaoDb.getAllByCategoryId(id);

                categoryList.add(new Category(id, name, productList));
            }

            return categoryList;
        } catch (SQLException e) {
            throw new DaoException("Failed to get all categories", e);
        }
    }

    @Override
    public Optional<Category> getByName(String categoryName) throws DaoException {

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CATEGORY_BY_NAME)) {

            Category category = null;

            ps.setString(1, categoryName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(CATEGORY_ID_COLUMN);
                    String name = rs.getString(CATEGORY_NAME_COLUMN);
                    List<Product> productList = productDaoDb.getAllByCategoryId(id);

                    category = new Category(id, name, productList);
                }
            }

            return Optional.ofNullable(category);
        } catch (SQLException e) {
            throw new DaoException("Failed to get category by name: " + categoryName, e);
        }
    }


    @Override
    public Optional<Category> getById(int categoryId) {

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CATEGORY_BY_ID)) {
            Category category = null;

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(CATEGORY_ID_COLUMN);
                    String name = rs.getString(CATEGORY_NAME_COLUMN);
                    List<Product> productList = productDaoDb.getAllByCategoryId(id);

                    category = new Category(id, name, productList);
                }
            }

            return Optional.ofNullable(category);
        } catch (SQLException e) {
            throw new DaoException("Failed to get category by categoryId: " + categoryId, e);
        }
    }


}
