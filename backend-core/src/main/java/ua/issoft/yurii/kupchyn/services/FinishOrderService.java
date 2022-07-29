package ua.issoft.yurii.kupchyn.services;

import ua.issoft.yurii.kupchyn.entities.Order;
import ua.issoft.yurii.kupchyn.entities.OrderStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

public class FinishOrderService extends Thread {

    private static final int INTERVAL_TIME_MILLISECONDS = 2000;
    private final OrderService orderService;

    public FinishOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(INTERVAL_TIME_MILLISECONDS);

                Consumer<Order> orderConsumer = (order) -> {
                    LocalDateTime nowTime = LocalDateTime.now();
                    LocalDateTime finishOrderTime = order
                            .getExecutionStart()
                            .plus(order.getExecutionTime(), ChronoUnit.SECONDS);

                    if (nowTime.isAfter(finishOrderTime)) {
                        order.setOrderStatus(OrderStatus.COMPLETED);

                        System.out.println();
                        System.out.printf("Order № %d has been updated: status = COMPLETED", order.getOrderId());
                        System.out.println();
                    }
                };

                orderService.updateAllByStatus(OrderStatus.PERFORMED, orderConsumer);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
