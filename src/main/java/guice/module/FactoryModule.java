package guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import guice.factory.OrderFactory;
import guice.factory.OrderFactoryImpl;
import guice.factory.PersonFactory;
import guice.factory.PersonFactoryImpl;

/**
 * Created by timp on 13/11/2015.
 */
public class FactoryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PersonFactory.class).to(PersonFactoryImpl.class).in(Singleton.class);
        bind(OrderFactory.class).to(OrderFactoryImpl.class).in(Singleton.class);
    }
}
