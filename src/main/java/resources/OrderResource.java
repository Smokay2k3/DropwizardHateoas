package resources;

import java.util.*;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import DAO.OrderDAO;
import com.google.inject.Inject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import guice.providers.OrderProvider;
import model.Order;
import model.Person;
import org.mongojack.JacksonDBCollection;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    public static final String PATH_FOR_PERSON = "/person/";

    private final OrderDAO dao;

    @Inject
    public OrderResource(OrderDAO dao){
        this.dao = dao;
    }

    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("orderId") String orderId) {
        return dao.getById(orderId);
    }

    @GET
    public List<Order> get() {
        return dao.getAll();
    }

    @GET
    @Path(PATH_FOR_PERSON + "{personId}")
    public List<Order> getForPerson(@PathParam("personId") String personId) {
        return get().stream()
                .filter(o -> o.getPersonId().equals(personId))
                .collect(Collectors.toList());
    }

    @POST
    public Order add(Order order) {
        dao.add(order);
        return order;
    }

    @DELETE
    @Path("/{orderId}")
    public Order delete(@PathParam("orderId") String orderId){
        Order orderBeingDeleted = dao.getById(orderId);
        dao.removeById(orderId);

        return orderBeingDeleted;
    }

    public Optional<String> getPersonIdFromOrder(String orderId){
        Order order = getOrder(orderId);

        if(order != null){
            return Optional.of(order.getPersonId());
        }

        return Optional.empty();
    }
}