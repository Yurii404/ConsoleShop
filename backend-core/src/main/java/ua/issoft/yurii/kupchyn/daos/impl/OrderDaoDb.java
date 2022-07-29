package ua.issoft.yurii.kupchyn.daos.impl;

import ua.issoft.yurii.kupchyn.daos.ConnectionPool;
import ua.issoft.yurii.kupchyn.daos.DaoException;
import ua.issoft.yurii.kupchyn.daos.OrderDao;
import ua.issoft.yurii.kupchyn.entities.Order;
import ua.issoft.yurii.kupchyn.entities.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoDb implements OrderDao {

    private final ConnectionPool connectionPool;

    private static final String PRODUCT_ID_COLUMN = "productId";
    private static final String ORDER_STATUS_COLUMN = "orderStatus";
    private static final String ORDER_Id_COLUMN = "orderId";
    private static final String ORDER_START_EXECUTION_COLUMN = "dateOfStartExecution";
    private static final String ORDER_EXECUTION_TIME_COLUMN = "executionTime";

    private static final String INSERT_ORDER_QUERY = "INSERT INTO " +
            "orders(orderId, productId, orderStatus) VALUES (?, ?, ?);";

    private static final String SELECT_ORDER_BY_ID =
            "SELECT orderId, productId, orderStatus, dateOfStartExecution, executionTime " +
                    "FROM orders WHERE orderId=?;";

    private static final String SELECT_ORDERS =
            "SELECT orderId, productId, orderStatus, dateOfStartExecution, executionTime FROM orders;";

    private static final String UPDATE_ORDER_QUERY = "UPDATE orders " +
            "SET orderStatus=?, dateOfStartExecution=?, executionTime=? WHERE orderId=?;";


    public OrderDaoDb(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void create(Order order) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ORDER_QUERY)) {

            ps.setInt(1, order.getOrderId());
            ps.setInt(2, order.getProductId());
            ps.setString(3, String.valueOf(order.getOrderStatus()));

            ps.execute();
        } catch (SQLException e) {
            throw new DaoException("Failed to create order: " + order, e);
        }
    }

    @Override
    public Optional<Order> getById(int id) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ORDER_BY_ID)) {
            Order order = null;

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LocalDateTime dateOfStartExecution = null;

                    int orderId = rs.getInt(ORDER_Id_COLUMN);
                    int productId = rs.getInt(PRODUCT_ID_COLUMN);
                    OrderStatus orderStatus = OrderStatus.valueOf(rs.getString(ORDER_STATUS_COLUMN).toUpperCase());
                    if (rs.getTimestamp(ORDER_START_EXECUTION_COLUMN) != null) {
                        dateOfStartExecution = rs.getTimestamp(ORDER_START_EXECUTION_COLUMN).toLocalDateTime();
                    }
                    int executionTime = rs.getInt(ORDER_EXECUTION_TIME_COLUMN);

                    order = new Order(orderId, productId, orderStatus, dateOfStartExecution, executionTime);
                }
            }

            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DaoException("Failed to get order by orderId: " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orderList = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ORDERS)) {

            while (rs.next()) {
                LocalDateTime dateOfStartExecution = null;

                int orderId = rs.getInt(ORDER_Id_COLUMN);
                int productId = rs.getInt(PRODUCT_ID_COLUMN);
                OrderStatus orderStatus = OrderStatus.valueOf(rs.getString(ORDER_STATUS_COLUMN).toUpperCase());
                if (rs.getTimestamp(ORDER_START_EXECUTION_COLUMN) != null) {
                    dateOfStartExecution = rs.getTimestamp(ORDER_START_EXECUTION_COLUMN).toLocalDateTime();
                }
                int executionTime = rs.getInt(ORDER_EXECUTION_TIME_COLUMN);

                orderList.add(new Order(orderId, productId, orderStatus, dateOfStartExecution, executionTime));
            }

            return orderList;
        } catch (SQLException e) {
            throw new DaoException("Failed to get all orders", e);
        }
    }

    @Override
    public void update(Order updatedOrder) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_ORDER_QUERY)) {

            ps.setString(1, String.valueOf(updatedOrder.getOrderStatus()));
            ps.setTimestamp(2, Timestamp.valueOf(updatedOrder.getExecutionStart()));
            ps.setInt(3, updatedOrder.getExecutionTime());
            ps.setInt(4, updatedOrder.getOrderId());

            ps.execute();
        } catch (SQLException e) {
            throw new DaoException("Failed to update order: " + updatedOrder, e);
        }
    }
}
