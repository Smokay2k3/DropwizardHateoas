package hateoas;

import resources.OrderResource;
import resources.PersonResource;

/**
 * Created by timp on 16/11/2015.
 */
public class OrderLinks extends Links {

    public OrderLinks(String orderId, String personId){
        if(orderId != null) {
            addLink("_self", OrderResource.class, orderId);
            addLink("person", PersonResource.class, personId);
            addLink("all", OrderResource.class);
        }
    }
}
