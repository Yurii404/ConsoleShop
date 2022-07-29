package ua.issoft.yurii.kupchyn.commands.printers;

import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.entities.Product;
import ua.issoft.yurii.kupchyn.services.sorting.SortProductService;
import ua.issoft.yurii.kupchyn.services.sorting.SortProductServiceException;
import ua.issoft.yurii.kupchyn.services.sorting.comparators.ProductComparatorException;

import java.util.List;

public class CategoryPrinter {
    private final SortProductService sortProductService;

    public CategoryPrinter(SortProductService sortProductService) {
        this.sortProductService = sortProductService;
    }

    public void printSortedProducts(Category category) {
        try {
            List<Product> products = sortProductService.sortProducts(category);

            System.out.println(category.getName() + " :");
            for (int i = 0; i < products.size(); i++) {
                Product currentProduct = products.get(i);
                System.out.printf("%d. %s %s - %d$", i + 1, currentProduct.getProducer(), currentProduct.getName(), currentProduct.getPrice());
                System.out.println();
            }

        } catch (SortProductServiceException e) {
            printProduct(category);
        }
    }

    public void printProduct(Category category) {
        List<Product> products = category.getProducts();

        System.out.println(category.getName() + " :");
        for (int i = 0; i < products.size(); i++) {
            Product currentProduct = products.get(i);
            System.out.printf("%d. %s %s - %d$", currentProduct.getId(), currentProduct.getProducer(), currentProduct.getName(), currentProduct.getPrice());
            System.out.println();
        }
    }

    public void printFiveTheMostExpensiveProduct(Category category) throws CategoryPrinterException {
        if (!category.getProducts().isEmpty()) {
            try {
                int sumOfFiveTheMostExpensiveProduct = sortProductService.sumFiveTheMostExpensiveProduct(category);

                System.out.println();
                System.out.println(category.getName() + " :");
                System.out.println("Sum of five the most expensive product: " + sumOfFiveTheMostExpensiveProduct);

            } catch (ProductComparatorException | SortProductServiceException e) {
                throw new CategoryPrinterException("Failed to sum products", e);
            }
        } else {
            System.out.println("\nThere are no products in the category \"" + category.getName() + "\"");
        }

    }

}

