package ua.issoft.yurii.kupchyn.commands.printers;

import ua.issoft.yurii.kupchyn.entities.Order;

import java.util.List;

public class OrderPrinter {

    public void printOrder(List<Order> orderList) {
        System.out.println();
        System.out.println("Orders:");
        for (Order order : orderList) {
            System.out.printf("№ %d | %s", order.getOrderId(), order.getOrderStatus());
            System.out.println();
        }
    }
}
