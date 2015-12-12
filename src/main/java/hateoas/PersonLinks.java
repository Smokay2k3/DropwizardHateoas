package hateoas;

import resources.OrderResource;
import resources.PersonResource;

/**
 * Created by timp on 16/11/2015.
 */
public class PersonLinks extends Links {
    public PersonLinks(String personId){
        if(personId != null) {
            addLink("_self", PersonResource.class, String.valueOf(personId));
            addLink("orders", OrderResource.class, OrderResource.PATH_FOR_PERSON + personId);
            addLink("all", PersonResource.class);
        }
    }
}
