package ua.issoft.yurii.kupchyn.services;

import ua.issoft.yurii.kupchyn.daos.OrderDao;
import ua.issoft.yurii.kupchyn.entities.Order;
import ua.issoft.yurii.kupchyn.entities.OrderStatus;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class OrderService {
    private final OrderDao orderDao;
    private final List<Order> orderList;
    private static final int INTERVAL_SYNCHRONISATION_TIME = 5000;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
        orderList = Collections.synchronizedList(orderDao.getAll());
        runSynchronisationWithDb();
    }

    public void createOrder(Order order) {
        order.setOrderId(orderList.size());
        orderList.add(order);
    }

    public List<Order> getAll() {
        return orderList;
    }

    public void updateAllByStatus(OrderStatus status, Consumer<Order> consumer) {
        synchronized (orderList) {
            orderList.stream()
                    .filter(order -> order.getOrderStatus().equals(status))
                    .forEach(consumer);
        }
    }

    private void runSynchronisationWithDb() {
        new Thread(() -> {
            while (true) {
                try {

                    Thread.sleep(INTERVAL_SYNCHRONISATION_TIME);

                    synchronized (orderList) {
                        List<Order> orderListMem = orderList;
                        List<Order> orderListDb = orderDao.getAll();


                        if (orderListMem.size() != orderListDb.size()) {
                            for (int i = orderListMem.size() - 1; i >= orderListDb.size(); i--) {
                                orderDao.create(orderListMem.get(i));
                            }
                        }

                        for (Order orderMem : orderListMem) {
                            for (Order orderDb : orderListDb) {
                                if (orderMem.getOrderId() == orderDb.getOrderId()) {
                                    if (!checkEqualsOrder(orderMem, orderDb)) {
                                        orderDao.update(orderMem);
                                    }
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }

    private boolean checkEqualsOrder(Order orderMemory, Order orderDatabase) {
        boolean result = true;

        if (orderMemory.getOrderStatus() != orderDatabase.getOrderStatus()) {
            result = false;
        } else if (orderMemory.getExecutionStart() != orderDatabase.getExecutionStart()) {
            result = false;
        } else if (orderMemory.getExecutionTime() != orderDatabase.getExecutionTime()) {
            result = false;
        }

        return result;
    }


}
