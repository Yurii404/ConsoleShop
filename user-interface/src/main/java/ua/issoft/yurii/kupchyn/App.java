package ua.issoft.yurii.kupchyn;


import ua.issoft.yurii.kupchyn.commands.AddCategoryToCatalogCommand;
import ua.issoft.yurii.kupchyn.commands.AddProductToCategoryCommand;
import ua.issoft.yurii.kupchyn.commands.Command;
import ua.issoft.yurii.kupchyn.commands.ExitCommand;
import ua.issoft.yurii.kupchyn.commands.OrderProductCommand;
import ua.issoft.yurii.kupchyn.commands.PrintCatalogCommand;
import ua.issoft.yurii.kupchyn.commands.PrintOrdersCommand;
import ua.issoft.yurii.kupchyn.commands.PrintSumOfFiveExpensiveProductCommand;
import ua.issoft.yurii.kupchyn.commands.printers.CatalogPrinter;
import ua.issoft.yurii.kupchyn.commands.printers.CategoryPrinter;
import ua.issoft.yurii.kupchyn.commands.printers.OrderPrinter;
import ua.issoft.yurii.kupchyn.daos.CategoryDao;
import ua.issoft.yurii.kupchyn.daos.OrderDao;
import ua.issoft.yurii.kupchyn.daos.ProductDao;
import ua.issoft.yurii.kupchyn.daos.impl.CategoryDaoDb;
import ua.issoft.yurii.kupchyn.daos.impl.ConnectionPoolImpl;
import ua.issoft.yurii.kupchyn.daos.impl.OrderDaoMemory;
import ua.issoft.yurii.kupchyn.daos.impl.ProductDaoDb;
import ua.issoft.yurii.kupchyn.services.CatalogService;
import ua.issoft.yurii.kupchyn.services.CategoryService;
import ua.issoft.yurii.kupchyn.services.ExecuteOrderService;
import ua.issoft.yurii.kupchyn.services.FinishOrderService;
import ua.issoft.yurii.kupchyn.services.OrderService;
import ua.issoft.yurii.kupchyn.services.ProductService;
import ua.issoft.yurii.kupchyn.services.sorting.SortProductService;

import java.util.HashMap;
import java.util.Map;

public class App {
    private CategoryService categoryService;
    private CatalogService catalogService;
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private ProductService productService;
    private SortProductService sortProductService;
    private CategoryPrinter categoryPrinter;
    private CatalogPrinter catalogPrinter;
    private OrderDao orderDao;
    private OrderService orderService;
    private OrderPrinter orderPrinter;
    private Menu menu;

    public App() {
        init();
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void init() {
        this.categoryDao = new CategoryDaoDb(ConnectionPoolImpl.getConnectionPool());
        this.productDao = new ProductDaoDb(ConnectionPoolImpl.getConnectionPool());
        this.orderDao = new OrderDaoMemory();

        this.productService = new ProductService(productDao);
        this.categoryService = new CategoryService(categoryDao);
        this.catalogService = new CatalogService();
        this.orderService = new OrderService(orderDao);
        this.sortProductService = new SortProductService("sort-config.xml");

        this.categoryPrinter = new CategoryPrinter(sortProductService);
        this.catalogPrinter = new CatalogPrinter(categoryPrinter, categoryService);
        this.orderPrinter = new OrderPrinter();

        this.menu = new Menu((HashMap<Integer, Command>) configureMenuItems());
    }

    private Map<Integer, Command> configureMenuItems() {
        Map<Integer, Command> menuItems = new HashMap<>();

        menuItems.put(1, new AddCategoryToCatalogCommand(categoryService, catalogService, productService, categoryPrinter));
        menuItems.put(2, new AddProductToCategoryCommand(categoryService, catalogService, productService, categoryPrinter));
        menuItems.put(3, new PrintCatalogCommand(catalogService, catalogPrinter));
        menuItems.put(4, new PrintSumOfFiveExpensiveProductCommand(categoryService, categoryPrinter));
        menuItems.put(5, new OrderProductCommand(orderService, categoryService, catalogService, productService, categoryPrinter));
        menuItems.put(6, new PrintOrdersCommand(orderService, orderPrinter));
        menuItems.put(7, new ExitCommand());

        return menuItems;
    }

    private void run() {
        runFinishOrderService();
        runExecuteOrderService();

        menu.run();
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