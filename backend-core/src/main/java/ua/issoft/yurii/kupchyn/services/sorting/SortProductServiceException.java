package ua.issoft.yurii.kupchyn.services.sorting;

public class SortProductServiceException extends Exception {
    public SortProductServiceException(String message) {
        super(message);
    }

    public SortProductServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
