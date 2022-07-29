package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.commands.printers.OrderPrinter;
import ua.issoft.yurii.kupchyn.entities.Order;
import ua.issoft.yurii.kupchyn.services.OrderService;

import java.util.List;
import java.util.Scanner;

public class PrintOrdersCommand implements Command {
    private final static String NAME = "Print orders";

    private final OrderService orderService;
    private final OrderPrinter orderPrinter;

    public PrintOrdersCommand(OrderService orderService, OrderPrinter orderPrinter) {
        this.orderService = orderService;
        this.orderPrinter = orderPrinter;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(Scanner scanner) {
        List<Order> orderList = orderService.getAll();
        orderPrinter.printOrder(orderList);
    }
}
