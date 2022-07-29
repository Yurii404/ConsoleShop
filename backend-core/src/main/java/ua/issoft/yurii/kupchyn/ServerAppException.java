package ua.issoft.yurii.kupchyn;

public class ServerAppException extends RuntimeException {
    public ServerAppException(String message) {
        super(message);
    }

    public ServerAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
