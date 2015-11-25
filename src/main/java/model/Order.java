package model;

import hateoas.Links;
import hateoas.OrderLinks;

public class Order {
    private final Long orderId;
    private final Long personId;

    private final String description;

    private Links links;

    public Order(Long orderId, Long personId, String description) {
        this.orderId = orderId;
        this.personId = personId;
        this.description = description;

        this.links = new OrderLinks(orderId);
    }

    public Order() {
        this.orderId = null;
        this.personId = null;
        this.description = null;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getDescription() {
        return description;
    }

    public Links getLinks() {
        return links;
    }

}
