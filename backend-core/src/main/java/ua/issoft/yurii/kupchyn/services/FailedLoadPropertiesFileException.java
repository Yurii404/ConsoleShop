package ua.issoft.yurii.kupchyn.services;

public class FailedLoadPropertiesFileException extends RuntimeException {
    public FailedLoadPropertiesFileException() {
    }

    public FailedLoadPropertiesFileException(String message) {
        super(message);
    }

    public FailedLoadPropertiesFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
