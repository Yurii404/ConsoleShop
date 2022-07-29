package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.ServerClientProtocol;
import ua.issoft.yurii.kupchyn.entities.Catalog;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.requests.GetCatalogRequest;
import ua.issoft.yurii.kupchyn.requests.OrderProductRequest;
import ua.issoft.yurii.kupchyn.responses.GetCatalogResponse;
import ua.issoft.yurii.kupchyn.responses.OrderProductResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class OrderProductCommand implements Command {
    private static final String NAME = "Order product";
    ServerClientProtocol serverClientProtocol;

    public OrderProductCommand(ServerClientProtocol serverClientProtocol) {
        this.serverClientProtocol = serverClientProtocol;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(BufferedReader consoleIn) {
        try {
            serverClientProtocol.sendRequest(new GetCatalogRequest());

            GetCatalogResponse getCatalogResponse = (GetCatalogResponse) serverClientProtocol.receiveResponse();

            Catalog catalog = getCatalogResponse.getCatalog();

            int productToOrderId = selectProductToOrder(consoleIn, catalog);

            serverClientProtocol.sendRequest(new OrderProductRequest(productToOrderId));



            OrderProductResponse orderProductResponse = (OrderProductResponse) serverClientProtocol.receiveResponse();

            String message = orderProductResponse.getMessage();

            System.out.println(message);

        } catch (IOException | ClassNotFoundException e) {
            throw new FailedExecuteCommandException("Failed execute command " + NAME + " : " + e);
        }
    }

    private int selectProductToOrder(BufferedReader consoleIn, Catalog catalog) throws IOException {

        System.out.println();
        System.out.println("Catalog: ");
        catalog.getCategories().forEach(category -> {
            System.out.printf("%d. %s \n", category.getId(), category.getName());
        });
        System.out.println();
        System.out.print("Enter id of category and press enter: ");
        int categoryId = Integer.parseInt(consoleIn.readLine());

        Category workCategory = catalog.getCategories().get(categoryId - 1);

        System.out.println();
        System.out.println(workCategory.getName() + ": ");
        workCategory.getProducts().forEach(product -> {
            System.out.printf("%d. %s %s - %d$", product.getId(), product.getProducer(), product.getName(), product.getPrice());
            System.out.println();
        });
        System.out.println();
        System.out.print("Enter id of product and press enter: ");

        return Integer.parseInt(consoleIn.readLine());
    }
}
