package resources;

import static com.google.common.collect.Iterables.transform;

import java.util.*;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import guice.providers.OrderBuilderProvider;
import model.Order;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    public static final String PATH_FOR_PERSON = "/person/";

    private final OrderBuilderProvider provider;

    private Map<Long, Order> orders = new HashMap<>();
    private Long currentOrderId = 0l;

    @Inject
    public OrderResource(OrderBuilderProvider provider){
        this.provider = provider;
    }

    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("orderId") Long orderId) {
        return orders.get(orderId);
    }

    @GET
    public List<Order> get() {
        return orders.values().stream()
                .sorted(Comparator.comparing(Order::getOrderId))
                .collect(Collectors.toList());
    }

    @GET
    @Path(PATH_FOR_PERSON + "{personId}")
    public List<Order> getForPerson(@PathParam("personId") Long personId) {
        return orders.values().stream()
                .filter(o -> o.getPersonId().equals(personId))
                .collect(Collectors.toList());
    }

    @POST
    public Order add(Order order) {
        Order newOrder = provider.get()
                .withOrderId(currentOrderId).withPersonId(order.getPersonId())
                .withDescription(order.getDescription()).build();

        orders.put(currentOrderId, newOrder);
        currentOrderId++;

        return newOrder;
    }

    public Optional<Long> getPersonIdFromOrder(String orderId){
        Order order = orders.get(orderId);

        if(order != null){
            return Optional.of(order.getPersonId());
        }

        return Optional.empty();
    }
}