package ua.issoft.yurii.kupchyn.requests;

public class OrderProductRequest implements Request {
    private static final String NAME = "orderProduct";
    private final int productId;

    public OrderProductRequest(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
