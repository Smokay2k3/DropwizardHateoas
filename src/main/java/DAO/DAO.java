package DAO;

import java.util.List;

/**
 * Created by timp on 11/12/15.
 */
public interface DAO<T, U> {

    List<T> getAll();

    T getById(U id);

    T add(T object);

    T removeById(U id);
}
