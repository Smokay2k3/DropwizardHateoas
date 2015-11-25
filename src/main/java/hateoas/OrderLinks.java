package hateoas;

import resources.OrderResource;

/**
 * Created by timp on 16/11/2015.
 */
public class OrderLinks extends Links {

    public OrderLinks(Long orderId){
        if(orderId != null) {
            addLink("_self", OrderResource.class, String.valueOf(orderId));
            addLink("all", OrderResource.class);
        }
    }
}
