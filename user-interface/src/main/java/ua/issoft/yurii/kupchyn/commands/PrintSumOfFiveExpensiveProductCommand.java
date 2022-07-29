package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.commands.printers.CategoryPrinter;
import ua.issoft.yurii.kupchyn.commands.printers.CategoryPrinterException;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.services.CategoryService;

import java.util.List;
import java.util.Scanner;

public class PrintSumOfFiveExpensiveProductCommand implements Command {
    private final static String NAME = "Print sum of five the most expensive product of each category";
    private final CategoryService categoryService;
    private final CategoryPrinter categoryPrinter;

    public PrintSumOfFiveExpensiveProductCommand(CategoryService categoryService, CategoryPrinter categoryPrinter) {
        this.categoryService = categoryService;
        this.categoryPrinter = categoryPrinter;
    }


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(Scanner scanner) {
        List<Category> workCategories = categoryService.getAllCategories();

        for (Category category : workCategories) {
            try {
                categoryPrinter.printFiveTheMostExpensiveProduct(category);
            } catch (CategoryPrinterException e) {
                System.out.println("\nFailed to sum: " + e.getMessage());
            }
        }
    }
}
