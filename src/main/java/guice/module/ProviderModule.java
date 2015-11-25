package guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import guice.providers.OrderBuilderProvider;
import guice.providers.OrderBuilderProviderImpl;
import guice.providers.PersonBuilderProvider;
import guice.providers.PersonBuilderProviderImpl;

/**
 * Created by timp on 13/11/2015.
 */
public class ProviderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PersonBuilderProvider.class).to(PersonBuilderProviderImpl.class).in(Singleton.class);
        bind(OrderBuilderProvider.class).to(OrderBuilderProviderImpl.class).in(Singleton.class);
    }
}
