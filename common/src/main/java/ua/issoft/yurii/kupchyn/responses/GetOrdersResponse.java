package ua.issoft.yurii.kupchyn.responses;

import ua.issoft.yurii.kupchyn.entities.Order;

import java.util.List;

public class GetOrdersResponse implements Response {
    private final List<Order> orderList;

    public GetOrdersResponse(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

}
