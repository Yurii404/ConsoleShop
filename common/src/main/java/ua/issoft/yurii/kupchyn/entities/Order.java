package ua.issoft.yurii.kupchyn.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Serializable {
    private int orderId;
    private final int productId;
    private OrderStatus orderStatus;
    private LocalDateTime executionStart;
    private int executionTime;

    public Order(int productId, OrderStatus orderStatus) {
        this.productId = productId;
        this.orderStatus = orderStatus;
    }

    public Order(int orderId, int productId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderStatus = orderStatus;
    }

    public Order(int orderId, int productId, OrderStatus orderStatus, LocalDateTime executionStart, int executionTime) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderStatus = orderStatus;
        this.executionStart = executionStart;
        this.executionTime = executionTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getExecutionStart() {
        return executionStart;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setExecutionStart(LocalDateTime executionStart) {
        this.executionStart = executionStart;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && productId == order.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", orderStatus=" + orderStatus +
                ", dateOfStartExecution=" + executionStart +
                ", executionTime=" + executionTime +
                '}';
    }
}


