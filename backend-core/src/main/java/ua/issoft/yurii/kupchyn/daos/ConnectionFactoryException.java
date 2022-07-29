package ua.issoft.yurii.kupchyn.daos;

public class ConnectionFactoryException extends RuntimeException {
    public ConnectionFactoryException() {
    }

    public ConnectionFactoryException(String message) {
        super(message);
    }

    public ConnectionFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
