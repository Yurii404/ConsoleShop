package ua.issoft.yurii.kupchyn;

public class ClientAppException extends RuntimeException {
    public ClientAppException(String message) {
        super(message);
    }

    public ClientAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
