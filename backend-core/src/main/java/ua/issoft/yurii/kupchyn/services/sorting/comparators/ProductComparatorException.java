package ua.issoft.yurii.kupchyn.services.sorting.comparators;

public class ProductComparatorException extends RuntimeException {
    public ProductComparatorException(String message) {
        super(message);
    }

    public ProductComparatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
