package ua.issoft.yurii.kupchyn.responses;

public class FailedResponse implements Response{
    private String message;

    public FailedResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
