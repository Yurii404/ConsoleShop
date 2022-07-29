package ua.issoft.yurii.kupchyn.daos;

import ua.issoft.yurii.kupchyn.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    void create(Order order);

    Optional<Order> getById(int id);

    List<Order> getAll();

    void update(Order updatedOrder);
}
