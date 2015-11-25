package storage;

import java.util.HashMap;
import java.util.Map;

import model.Person;

/**
 * Created by timp on 13/11/2015.
 */
public class BasicPersonStorage implements Storage<Person, Long> {

    private Map<Long,Person> people = new HashMap<>();

    @Override
    public Person get(Long selectionCriteria) {
        return people.get(selectionCriteria);
    }

    @Override
    public void saveObject(Person object) {
        people.put(object.getPersonId(), object);
    }

    @Override
    public Map<Long, Person> getAll() {
        return people;
    }

}
