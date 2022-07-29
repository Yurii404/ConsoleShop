package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.commands.printers.CategoryPrinter;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.entities.Product;
import ua.issoft.yurii.kupchyn.services.CatalogService;
import ua.issoft.yurii.kupchyn.services.CategoryService;
import ua.issoft.yurii.kupchyn.services.ProductService;

import java.util.Optional;
import java.util.Scanner;

public class AddProductToCategoryCommand extends AbstractCommand {
    private static final String NAME = "Add new product to catalog";

    public AddProductToCategoryCommand(CategoryService categoryService, CatalogService catalogService,
                                       ProductService productService, CategoryPrinter categoryPrinter) {
        super(categoryService, catalogService, productService, categoryPrinter);
    }

    @Override
    public void execute(Scanner input) {
        Optional<Category> category = selectCategory(input);

        if (category.isPresent()) {
            int categoryId = category.get().getId();
            String producer;
            String name;
            int price;
            System.out.println();

            System.out.println("Create new product:");
            System.out.print("Enter producer: ");
            input.nextLine();
            producer = input.nextLine();
            System.out.print("Enter name: ");
            name = input.nextLine();
            System.out.print("Enter price: ");
            price = input.nextInt();

            Product newProduct = new Product(categoryId, producer, name, price);

            productService.addProductInCategory(categoryId, newProduct);

            System.out.println();
            System.out.println("Product was successful added!");
            System.out.println();

        } else {
            System.out.println("Invalid id category!");
        }
    }

    public String getName() {
        return NAME;
    }


}
