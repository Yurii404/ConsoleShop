package ua.issoft.yurii.kupchyn.daos.impl;

import ua.issoft.yurii.kupchyn.daos.OrderDao;
import ua.issoft.yurii.kupchyn.entities.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderDaoMemory implements OrderDao {

    private final List<Order> orders;

    public OrderDaoMemory() {
        this.orders = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void create(Order order) {
        orders.add(order);
    }

    @Override
    public Optional<Order> getById(int id) {
        return orders.stream().filter(order -> order.getOrderId() == id).findAny();
    }

    @Override
    public List<Order> getAll() {
        return orders;
    }

    @Override
    public void update(Order updatedOrder) {
        Optional<Order> oldOrder = orders.stream()
                .filter(order -> order.getOrderId() == updatedOrder.getOrderId())
                .findAny();

        oldOrder.ifPresent(order -> {
            order.setExecutionTime(updatedOrder.getExecutionTime());
            order.setOrderStatus(updatedOrder.getOrderStatus());
            order.setExecutionStart(updatedOrder.getExecutionStart());
        });
    }
}
