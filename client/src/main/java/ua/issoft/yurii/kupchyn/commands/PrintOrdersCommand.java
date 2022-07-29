package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.ServerClientProtocol;
import ua.issoft.yurii.kupchyn.entities.Order;
import ua.issoft.yurii.kupchyn.requests.GetOrdersRequest;
import ua.issoft.yurii.kupchyn.responses.GetOrdersResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class PrintOrdersCommand implements Command {
    private static final String NAME = "Print orders";
    ServerClientProtocol serverClientProtocol;

    public PrintOrdersCommand(ServerClientProtocol serverClientProtocol) {
        this.serverClientProtocol = serverClientProtocol;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(BufferedReader consoleIn) {
        try {
            serverClientProtocol.sendRequest(new GetOrdersRequest());

            GetOrdersResponse response = (GetOrdersResponse) serverClientProtocol.receiveResponse();

            List<Order> orderList = response.getOrderList();

            System.out.println();
            System.out.println("Orders:");
            for (Order order : orderList) {
                System.out.printf("№ %d | %s", order.getOrderId(), order.getOrderStatus());
                System.out.println();
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new FailedExecuteCommandException("Failed execute command " + NAME + " : " + e);
        }
    }
}
