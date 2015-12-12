package guice.providers;

import model.Person;

public class PersonProviderImpl implements PersonProvider {

    @Override
    public Person get() {
        return new Person();
    }
}
