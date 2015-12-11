package model;

import hateoas.Links;
import hateoas.OrderLinks;

public class Order {

    private final Long orderId;
    private final Long personId;
    private final String description;
    private final Links links;

    public Order() {
        this.orderId = null;
        this.personId = null;
        this.description = null;
        this.links = null;
    }

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.personId = builder.personId;
        this.description = builder.description;
        this.links = new OrderLinks(orderId);
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

    public static class Builder{
        private Long orderId;
        private Long personId;
        private String description;

        public Builder withOrderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder withPersonId(Long personId) {
            this.personId = personId;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Order build(){
            return new Order(this);
        }
    }

}
