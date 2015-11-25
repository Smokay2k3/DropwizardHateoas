package guice.providers;

import model.Person;

public class PersonBuilderProviderImpl implements PersonBuilderProvider {

    @Override
    public Person.Builder get() {
        return new Person.Builder();
    }
}
