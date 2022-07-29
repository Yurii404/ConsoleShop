package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.ServerClientProtocol;
import ua.issoft.yurii.kupchyn.entities.Catalog;
import ua.issoft.yurii.kupchyn.entities.Product;
import ua.issoft.yurii.kupchyn.requests.GetCatalogRequest;
import ua.issoft.yurii.kupchyn.responses.GetCatalogResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class PrintCatalogCommand implements Command {

    private static final String NAME = "Print catalog";
    ServerClientProtocol serverClientProtocol;

    public PrintCatalogCommand(ServerClientProtocol serverClientProtocol) {
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


            GetCatalogResponse response = (GetCatalogResponse) serverClientProtocol.receiveResponse();

            Catalog catalog = response.getCatalog();

            System.out.println();
            System.out.println("Catalog: ");
            catalog.getCategories().forEach(category -> {
                List<Product> products = category.getProducts();
                System.out.println(category.getName() + " :");
                for (Product currentProduct : products) {
                    System.out.printf("%d. %s %s - %d$", currentProduct.getId(), currentProduct.getProducer(), currentProduct.getName(), currentProduct.getPrice());
                    System.out.println();
                }
            });
            System.out.println();

        } catch (IOException | ClassNotFoundException e) {
            throw new FailedExecuteCommandException("Failed execute command " + NAME + " : " + e);
        }
    }
}
