package DAO;

import com.google.inject.Inject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import model.Order;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import java.util.List;

/**
 * Created by timp on 12/12/15.
 */
public class OrderMongoDAO implements OrderDAO {

    private static final String COLLECTION_NAME = "order";

    private final DB db;

    @Inject
    public OrderMongoDAO(DB db) {
        this.db = db;
    }

    @Override
    public JacksonDBCollection<Order, String> getCollection() {
        DBCollection dbCollection = db.getCollection(COLLECTION_NAME);
        return JacksonDBCollection.wrap(dbCollection, Order.class, String.class);
    }


    @Override
    public List<Order> getAll() {
        return getCollection().find().toArray();
    }

    @Override
    public Order getById(String id) {
        return getCollection().findOneById(id);
    }

    @Override
    public Order add(Order object) {
        object.setWriteLinks(false);
        WriteResult<Order, String> result = getCollection().insert(object);

        return result.getSavedObject();
    }

    @Override
    public Order removeById(String id) {
        JacksonDBCollection<Order, String> coll = getCollection();
        Order personBeingDeleted = coll.findOneById(id);
        coll.removeById(id);
        return personBeingDeleted;
    }
}
