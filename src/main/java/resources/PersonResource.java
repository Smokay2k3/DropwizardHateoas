package resources;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import guice.providers.PersonBuilderProvider;
import model.Person;
import storage.Storage;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final Storage<Person, Long> storage;

    private final PersonBuilderProvider provider;

    private long currentId = 0l;

    @Inject
    public PersonResource(PersonBuilderProvider provider, Storage basicPersonStorage){
        this.provider = provider;
        this.storage = basicPersonStorage;
    }

    @GET
    @Path("/{personId}")
    public Person getPerson(@PathParam("personId") Long personId) {
        return storage.get(personId);
    }

    @GET
    public List<Person> get() {
        return storage.getAll().values().stream()
                .sorted(Comparator.comparing(Person::getPersonId))
                .collect(Collectors.toList());
    }

    @POST
    public Person add(@Valid Person person) {
        Person newPerson = provider.get()
                .withPersonId(currentId).withFirstName(person.getFirstName())
                .withLastName(person.getLastName()).withContactInfo(person.getContactInfo())
                .build();

        storage.saveObject(newPerson);
        currentId++;

        return newPerson;
    }



}