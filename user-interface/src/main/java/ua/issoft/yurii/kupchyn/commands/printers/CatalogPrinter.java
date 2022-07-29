package ua.issoft.yurii.kupchyn.commands.printers;

import ua.issoft.yurii.kupchyn.entities.Catalog;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.services.CategoryService;

public class CatalogPrinter {
    private final CategoryPrinter categoryPrinter;
    private final CategoryService categoryService;

    public CatalogPrinter(CategoryPrinter categoryPrinter, CategoryService categoryService) {
        this.categoryPrinter = categoryPrinter;
        this.categoryService = categoryService;
    }

    public void printContent(Catalog catalog) {
        for (Category category : catalog.getCategories()) {
            category = categoryService.getByName(category.getName()).get();

            categoryPrinter.printSortedProducts(category);
        }
    }


}
