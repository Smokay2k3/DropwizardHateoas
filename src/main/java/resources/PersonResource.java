package resources;

import DAO.PersonDAO;
import com.google.inject.Inject;
import model.Person;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
    private final PersonDAO dao;

    @Inject
    public PersonResource(PersonDAO dao){
        this.dao = dao;
    }

    @GET
    @Path("/{personId}")
    public Person getPerson(@PathParam("personId") String personId) {
        return dao.getById(personId);
    }

    @GET
    public List<Person> get() {
        return dao.getAll();
    }

    @POST
    public Person add(Person person) {
        dao.add(person);

        return person;
    }

    @DELETE
    @Path("/{personId}")
    public Person delete(@PathParam("personId") String personId){
        Person personBeingDeleted = dao.getById(personId);
        dao.removeById(personId);

        return personBeingDeleted;
    }
}