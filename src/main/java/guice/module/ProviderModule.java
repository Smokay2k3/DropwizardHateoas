package guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import guice.providers.OrderProvider;
import guice.providers.OrderProviderImpl;
import guice.providers.PersonProvider;
import guice.providers.PersonProviderImpl;

/**
 * Created by timp on 13/11/2015.
 */
public class ProviderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PersonProvider.class).to(PersonProviderImpl.class).in(Singleton.class);
        bind(OrderProvider.class).to(OrderProviderImpl.class).in(Singleton.class);
    }
}
