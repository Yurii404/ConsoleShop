package ua.issoft.yurii.kupchyn.requests;

public class GetOrdersRequest implements Request {
    private static final String NAME = "getOrders";

    @Override
    public String getName() {
        return NAME;
    }
}
