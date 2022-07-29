package ua.issoft.yurii.kupchyn.commands;

public class FailedExecuteCommandException extends RuntimeException {
    public FailedExecuteCommandException(String message) {
        super(message);
    }

    public FailedExecuteCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
