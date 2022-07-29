package ua.issoft.yurii.kupchyn.services;

import ua.issoft.yurii.kupchyn.entities.Order;
import ua.issoft.yurii.kupchyn.entities.OrderStatus;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.function.Consumer;

public class ExecuteOrderService extends Thread {

    private static final int INTERVAL_TIME_MILLISECONDS = 1000;
    private final OrderService orderService;

    public ExecuteOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(INTERVAL_TIME_MILLISECONDS);

                Consumer<Order> orderConsumer = (order) -> {
                    order.setOrderStatus(OrderStatus.PERFORMED);
                    order.setExecutionStart(LocalDateTime.now());
                    order.setExecutionTime(new Random().nextInt(60));

                    System.out.println();
                    System.out.printf("Order № %d has been updated: status = PERFORMED", order.getOrderId());
                    System.out.println();
                };

                orderService.updateAllByStatus(OrderStatus.REGISTERED, orderConsumer);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
