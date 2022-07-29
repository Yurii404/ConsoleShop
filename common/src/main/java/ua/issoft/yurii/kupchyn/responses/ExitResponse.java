package ua.issoft.yurii.kupchyn.responses;

public class ExitResponse implements Response {
    private String message;

    public ExitResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
