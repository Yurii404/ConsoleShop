package ua.issoft.yurii.kupchyn;


import ua.issoft.yurii.kupchyn.daos.CategoryDao;
import ua.issoft.yurii.kupchyn.daos.OrderDao;
import ua.issoft.yurii.kupchyn.daos.impl.CategoryDaoDb;
import ua.issoft.yurii.kupchyn.daos.impl.ConnectionPoolImpl;
import ua.issoft.yurii.kupchyn.daos.impl.OrderDaoDb;
import ua.issoft.yurii.kupchyn.entities.Catalog;
import ua.issoft.yurii.kupchyn.entities.Order;
import ua.issoft.yurii.kupchyn.entities.OrderStatus;
import ua.issoft.yurii.kupchyn.requests.OrderProductRequest;
import ua.issoft.yurii.kupchyn.requests.Request;
import ua.issoft.yurii.kupchyn.responses.ExitResponse;
import ua.issoft.yurii.kupchyn.responses.FailedResponse;
import ua.issoft.yurii.kupchyn.responses.GetCatalogResponse;
import ua.issoft.yurii.kupchyn.responses.GetOrdersResponse;
import ua.issoft.yurii.kupchyn.responses.OrderProductResponse;
import ua.issoft.yurii.kupchyn.services.CatalogService;
import ua.issoft.yurii.kupchyn.services.CategoryService;
import ua.issoft.yurii.kupchyn.services.ExecuteOrderService;
import ua.issoft.yurii.kupchyn.services.FinishOrderService;
import ua.issoft.yurii.kupchyn.services.OrderService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    private static CategoryService categoryService;
    private static CatalogService catalogService;
    private static CategoryDao categoryDao;
    private static OrderDao orderDao;
    private static OrderService orderService;


    public ServerApp() {
        init();
    }

    public static void main(String[] args) {
        new ServerApp().run();
    }


    private void init() {
        categoryDao = new CategoryDaoDb(ConnectionPoolImpl.getConnectionPool());
        orderDao = new OrderDaoDb(ConnectionPoolImpl.getConnectionPool());
        categoryService = new CategoryService(categoryDao);
        catalogService = new CatalogService();
        orderService = new OrderService(orderDao);
    }

    private void run() {
        runExecuteOrderService();
        runFinishOrderService();

        try (ServerSocket serverSocket = new ServerSocket(4004)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new Server(clientSocket)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static class Server implements Runnable {

        private final Socket clientSocket;

        public Server(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    ServerClientProtocol serverClientProtocol = new ServerClientProtocol(clientSocket);
            ) {
                boolean needToExit = false;

                System.out.println("Client: " + clientSocket.getInetAddress() + " was opened");

                do {
                    Request request = serverClientProtocol.receiveRequest();

                    switch (request.getName()) {
                        case "getCatalog":
                            serverClientProtocol.sendResponse(new GetCatalogResponse(getCatalog()));
                            break;
                        case "orderProduct":
                            orderProduct(((OrderProductRequest) request).getProductId());

                            serverClientProtocol.sendResponse(new OrderProductResponse("Order was successful created!"));
                            break;
                        case "getOrders":
                            serverClientProtocol.sendResponse(new GetOrdersResponse(new ArrayList<>(getOrders())));
                            break;
                        case "exit":
                            serverClientProtocol.sendResponse(new ExitResponse("Goodbye!"));
                            needToExit = true;
                            break;
                        default:
                            serverClientProtocol.sendResponse(new FailedResponse("Invalid menu item"));
                            break;
                    }
                } while (!needToExit);

                System.out.println("Client: " + clientSocket.getInetAddress() + " was closed");

            } catch (Exception e) {
                throw new ServerAppException("Server failed " + e);
            }
        }

        private List<Order> getOrders() {
            return new ArrayList<>(orderService.getAll());
        }

        private Catalog getCatalog() {
            categoryService.getAllCategories()
                    .forEach(category -> catalogService.getCatalog().addCategory(category));

            return catalogService.getCatalog();
        }

        private void orderProduct(int productId) {
            orderService.createOrder(new Order(productId, OrderStatus.REGISTERED));
        }

    }

    private void runExecuteOrderService() {
        ExecuteOrderService executeOrderService = new ExecuteOrderService(orderService);
        executeOrderService.setDaemon(true);
        executeOrderService.start();
    }

    private void runFinishOrderService() {
        FinishOrderService finishOrderService = new FinishOrderService(orderService);
        finishOrderService.setDaemon(true);
        finishOrderService.start();
    }


}