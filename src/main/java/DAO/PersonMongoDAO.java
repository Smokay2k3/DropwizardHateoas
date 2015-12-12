package DAO;

import com.google.inject.Inject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import model.Person;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import java.util.List;

/**
 * Created by timp on 11/12/15.
 */
public class PersonMongoDAO implements PersonDAO {

    private static final String COLLECTION_NAME = "person";

    private final DB db;

    @Inject
    public PersonMongoDAO(DB db) {
        this.db = db;
    }

    @Override
    public JacksonDBCollection<Person, String> getCollection() {
        DBCollection dbCollection = db.getCollection(COLLECTION_NAME);
        return JacksonDBCollection.wrap(dbCollection, Person.class, String.class);
    }


    @Override
    public List<Person> getAll() {
        return getCollection().find().toArray();
    }

    @Override
    public Person getById(String id) {
        return getCollection().findOneById(id);
    }

    @Override
    public Person add(Person object) {
        object.setWriteLinks(false);
        WriteResult<Person, String> result = getCollection().insert(object);

        return result.getSavedObject();
    }

    @Override
    public Person removeById(String id) {
        JacksonDBCollection<Person, String> coll = getCollection();
        Person personBeingDeleted = coll.findOneById(id);
        return personBeingDeleted;
    }
}
