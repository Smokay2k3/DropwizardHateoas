package storage;

import java.util.Map;

/**
 * Created by timp on 13/11/2015.
 */
public interface Storage<T, U> {

    T get(U selectionCriteria);

    void saveObject(T object);

    Map<U, T> getAll();

}
