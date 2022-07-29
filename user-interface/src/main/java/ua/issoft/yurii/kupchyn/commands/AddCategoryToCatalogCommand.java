package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.commands.printers.CategoryPrinter;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.services.CatalogService;
import ua.issoft.yurii.kupchyn.services.CategoryService;
import ua.issoft.yurii.kupchyn.services.ProductService;

import java.util.List;
import java.util.Scanner;

public class AddCategoryToCatalogCommand extends AbstractCommand {
    private final static String NAME = "Add category to catalog";

    public AddCategoryToCatalogCommand(CategoryService categoryService, CatalogService catalogService,
                                       ProductService productService, CategoryPrinter categoryPrinter) {
        super(categoryService, catalogService, productService, categoryPrinter);
    }

    @Override
    public void execute(Scanner input) {
        int categoryId = selectCategory(input).get().getId();
        List<Category> categoryList = catalogService.getCatalog().getCategories();

        boolean existingInCatalog = categoryList.stream().anyMatch(item -> item.getId() == categoryId);

        if (!existingInCatalog) {
            if (categoryService.getById(categoryId).isPresent()) {
                Category addedCategory = categoryService.getById(categoryId).get();

                boolean categoryAlreadyExist = checkExistCategoryInCatalog(addedCategory.getName());

                if (!categoryAlreadyExist) {
                    catalogService.getCatalog().addCategory(addedCategory);

                    System.out.println();
                    System.out.println("Category was successful added!");
                    System.out.println();
                }
            } else {
                System.out.println();
                System.out.println("Invalid id category!");
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("Category is already exist!");
            System.out.println();
        }
    }

    public String getName() {
        return NAME;
    }

}
