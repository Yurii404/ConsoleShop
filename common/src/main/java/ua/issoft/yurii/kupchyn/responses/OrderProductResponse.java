package ua.issoft.yurii.kupchyn.responses;

public class OrderProductResponse implements Response {
    private String message;

    public OrderProductResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
