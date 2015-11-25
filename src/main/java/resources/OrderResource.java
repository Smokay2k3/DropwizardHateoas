package resources;

import static com.google.common.collect.Iterables.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import guice.factory.OrderFactory;
import model.Order;
import model.OrderListItem;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    public static final String PATH_FOR_PERSON = "/person/";

    private final OrderFactory orderFactory;

    private Map<Long, Order> orders = new HashMap<>();
    private Long currentOrderId = 0l;

    @Inject
    public OrderResource(OrderFactory orderFactory){
        this.orderFactory = orderFactory;
    }

    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("orderId") Long orderId) {
        return orders.get(orderId);
    }

    @GET
    public List<OrderListItem> get() {
        return Lists.newArrayList(transform(orders.values(),
                input -> new OrderListItem(input.getOrderId(), input.getDescription())));
    }

    @GET
    @Path(PATH_FOR_PERSON + "{personId}")
    public List<Order> getForPerson(@PathParam("personId") Long personId) {
        return orders.values().stream()
                .filter(o -> o.getPersonId().equals(personId))
                .collect(Collectors.toList());
    }

    @POST
    @Path("/{personId}")
    public Order add(Order order, @PathParam("personId") Long personId) {
        Order newOrder = orderFactory.create(currentOrderId, personId, order.getDescription());

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