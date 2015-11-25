package model;


public class OrderListItem {

    private final Long orderId;
    private final String description;

    public OrderListItem(Long orderId, String description){
        this.orderId = orderId;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Long getOrderId() {
        return orderId;
    }
}
