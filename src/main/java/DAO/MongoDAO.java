package DAO;

import model.Person;
import org.mongojack.JacksonDBCollection;

/**
 * Created by timp on 11/12/15.
 */
public interface MongoDAO<T, U> extends DAO<T, U> {

    JacksonDBCollection<T, U> getCollection();
}
