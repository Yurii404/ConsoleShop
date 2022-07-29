package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.commands.printers.CategoryPrinter;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.entities.Order;
import ua.issoft.yurii.kupchyn.entities.OrderStatus;
import ua.issoft.yurii.kupchyn.entities.Product;
import ua.issoft.yurii.kupchyn.services.CatalogService;
import ua.issoft.yurii.kupchyn.services.CategoryService;
import ua.issoft.yurii.kupchyn.services.OrderService;
import ua.issoft.yurii.kupchyn.services.ProductService;

import java.util.Optional;
import java.util.Scanner;

public class OrderProductCommand extends AbstractCommand {
    private final static String NAME = "Order some product";
    private final OrderService orderService;

    public OrderProductCommand(OrderService orderService, CategoryService categoryService, CatalogService catalogService,
                               ProductService productService, CategoryPrinter categoryPrinter) {
        super(categoryService, catalogService, productService, categoryPrinter);
        this.orderService = orderService;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(Scanner scanner) {
        Optional<Category> category = selectCategory(scanner);

        if (category.isPresent()) {
            Optional<Product> product = selectProduct(scanner, category.get());
            if (product.isPresent()) {
                orderService.createOrder(new Order(product.get().getId(), OrderStatus.REGISTERED));

                System.out.println();
                System.out.println("Order successful created!");
            } else {
                System.out.println("Invalid id product!");
            }
        } else {
            System.out.println("Invalid id category!");
        }
    }
}
