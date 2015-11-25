package guice.factory;

import com.google.inject.Inject;
import guice.bindingannotation.VerbosityConfigured;
import guice.impl.DetailLogger;
import model.ContactInfo;
import model.Person;

/**
 * Created by timp on 16/11/2015.
 */
public class PersonFactoryImpl implements PersonFactory {

    @Inject
    @VerbosityConfigured
    private DetailLogger logger;

    @Override
    public Person create(){
        logger.log("PersonFactoryImpl create called");
        return new Person();
    }

    @Override
    public Person create(Long personId, String firstName, String lastName, ContactInfo contactInfo) {
        logger.log("PersonFactoryImpl create called with params");
        return new Person(personId, firstName, lastName, contactInfo);
    }

    public void setLogger(DetailLogger logger) {
        this.logger = logger;
    }
}
