package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.commands.printers.CategoryPrinter;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.entities.Product;
import ua.issoft.yurii.kupchyn.services.CatalogService;
import ua.issoft.yurii.kupchyn.services.CategoryService;
import ua.issoft.yurii.kupchyn.services.ProductService;

import java.util.Optional;
import java.util.Scanner;

public abstract class AbstractCommand implements Command {

    protected CategoryService categoryService;
    protected CatalogService catalogService;
    protected ProductService productService;
    protected CategoryPrinter categoryPrinter;

    public AbstractCommand(CategoryService categoryService, CatalogService catalogService,
                           ProductService productService, CategoryPrinter categoryPrinter) {
        this.categoryService = categoryService;
        this.catalogService = catalogService;
        this.productService = productService;
        this.categoryPrinter = categoryPrinter;
    }

    public Optional<Category> selectCategory(Scanner input) {
        System.out.println("--------------------------------------------");
        System.out.println("Categories:");
        categoryService.getAllCategories().forEach(item -> {
            System.out.printf("%d. %s \n", item.getId(), item.getName());
        });
        System.out.println();
        System.out.print("Enter id of category and press enter: ");
        int idCategory = input.nextInt();

        return categoryService.getById(idCategory);
    }

    public boolean checkExistCategoryInCatalog(String nameAddedCategory) {
        for (int i = 0; i < catalogService.getCatalog().getCategories().size(); i++) {
            String currentName = catalogService.getCatalog().getCategories().get(i).getName();

            if (currentName.equals(nameAddedCategory)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Product> selectProduct(Scanner input, Category category) {

        System.out.println("--------------------------------------------");
        System.out.println("Select product:");
        categoryPrinter.printProduct(category);
        System.out.println();
        System.out.print("Enter id of product and press enter: ");

        int idProduct = input.nextInt();

        return productService.getById(idProduct);

    }
}
