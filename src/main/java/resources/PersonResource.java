package resources;

import static com.google.common.collect.Iterables.transform;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import guice.factory.PersonFactory;
import model.Person;
import model.PersonListItem;
import storage.Storage;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final Storage<Person, Long> storage;

    private final PersonFactory personFactory;

    private long currentId = 0l;

    @Inject
    public PersonResource(PersonFactory personFactory, Storage basicPersonStorage){
        this.personFactory = personFactory;
        this.storage = basicPersonStorage;
    }

    @GET
    @Path("/{personId}")
    public Person getPerson(@PathParam("personId") Long personId) {
        return storage.get(personId);
    }

    @GET
    public List<PersonListItem> get() {
        return Lists.newArrayList(transform(storage.getAll().values(),
                input -> new PersonListItem(input.getPersonId(), input.getFirstName())));
    }

    @POST
    public Person add(@Valid Person person) {
        Person newPerson = personFactory.create(
                currentId,
                person.getFirstName(),
                person.getLastName(),
                person.getContactInfo());

        storage.saveObject(newPerson);
        currentId++;

        return newPerson;
    }



}