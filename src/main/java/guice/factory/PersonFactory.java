package guice.factory;

import model.ContactInfo;
import model.Person;

/**
 * Created by timp on 16/11/2015.
 */
public interface PersonFactory {
    Person create();

    Person create(Long personId, String firstName, String lastName, ContactInfo contactInfo);
}
