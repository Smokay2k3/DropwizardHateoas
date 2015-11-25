package guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import guice.bindingannotation.Advanced;
import guice.bindingannotation.Basic;
import guice.bindingannotation.VerbosityConfigured;
import guice.impl.AdvancedLogger;
import guice.impl.BasicConsoleOutLogger;
import guice.impl.DefaultLogger;
import guice.impl.DetailLogger;
import guice.impl.QuietLogger;

/**
 * Created by timp on 13/11/2015.
 */
public class MultipleInstancesModule extends AbstractModule {

    @Override
    protected void configure() {
        //Changing config for verbosity
        bind(DetailLogger.class).annotatedWith(VerbosityConfigured.class).to(QuietLogger.class).in(Singleton.class);

        bind(DetailLogger.class).annotatedWith(Basic.class).to(BasicConsoleOutLogger.class).in(Singleton.class);
        bind(DetailLogger.class).annotatedWith(Advanced.class).to(AdvancedLogger.class).in(Singleton.class);
        bind(DetailLogger.class).to(DefaultLogger.class).in(Singleton.class);
    }
}
