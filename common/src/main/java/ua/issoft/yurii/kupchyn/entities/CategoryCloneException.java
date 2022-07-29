package ua.issoft.yurii.kupchyn.entities;

public class CategoryCloneException extends CloneNotSupportedException {
    public CategoryCloneException(String s) {
        super(s);
    }

    public CategoryCloneException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
