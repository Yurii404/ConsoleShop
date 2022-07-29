package ua.issoft.yurii.kupchyn.daos.impl;

import org.junit.Before;
import org.junit.Test;
import ua.issoft.yurii.kupchyn.entities.Order;
import ua.issoft.yurii.kupchyn.entities.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderDaoDbTest {
    private OrderDaoDb orderDaoDb;

    @Before
    public void setUp() throws Exception {
        orderDaoDb = new OrderDaoDb(ConnectionPoolImpl.getConnectionPool());
    }

    @Test
    public void should_createOrder() {
        Order order = new Order(1, OrderStatus.REGISTERED);

        orderDaoDb.create(order);
    }


    @Test
    public void should_returnOrderById() {
        boolean result = false;
        Optional<Order> order = orderDaoDb.getById(1);
        if (order.isPresent()) {
            result = order.get().getOrderId() == 1;
        }

        assertTrue(result);
    }

    @Test
    public void should_returnListOfOrders() {
        List<Order> orderList = orderDaoDb.getAll();
        boolean result = orderList.isEmpty();

        assertFalse(result);
    }

    @Test
    public void should_updateOrder() {
        boolean result = false;

        Optional<Order> oldOrder = orderDaoDb.getById(1);
        if (oldOrder.isPresent()) {
            oldOrder.get().setOrderStatus(OrderStatus.PERFORMED);
            oldOrder.get().setExecutionStart(LocalDateTime.now());
            orderDaoDb.update(oldOrder.get());
        }

        Optional<Order> updatedOrder = orderDaoDb.getById(1);
        if (updatedOrder.isPresent()) {
            result = updatedOrder.get().getOrderStatus().equals(OrderStatus.PERFORMED);
        }


        assertTrue(result);
    }


}