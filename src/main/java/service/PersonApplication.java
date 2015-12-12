package service;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import auth.BasicAuthenticator;
import auth.BasicAuthorizer;
import auth.model.User;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hubspot.dropwizard.guice.JerseyUtil;
import com.meltmedia.dropwizard.mongo.MongoBundle;
import guice.module.DAOModule;
import guice.module.MongoModule;
import guice.module.ProviderModule;
import guice.module.MultipleInstancesModule;
import healthcheck.TestDataHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import resources.OrderResource;
import resources.PersonResource;
import resources.ProtectedResource;
import resources.VersionResource;

public class PersonApplication extends Application<ServerConfiguration> {

    private MongoBundle<ServerConfiguration> mongoBundle;

    public static void main(String[] args) throws Exception {
        new PersonApplication().run(new String[]{"server", "config.yml"});
    }

    @Override
    public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
        bootstrap.addBundle(mongoBundle = MongoBundle.<ServerConfiguration>builder()
                .withConfiguration(ServerConfiguration::getMongo)
                .build());
    }

    @Override
    public void run(ServerConfiguration configuration, Environment environment) throws Exception {
        //Setup Auth
        setupAuthConfig(environment);

        //Create resources that need referencing
        Injector injector = createInjector(configuration);
        JerseyUtil.registerGuiceBound(injector, environment.jersey());

        PersonResource personResource = injector.getInstance(PersonResource.class);
        OrderResource orderResource = injector.getInstance(OrderResource.class);
        VersionResource versionResource = injector.getInstance(VersionResource.class);
        ProtectedResource protectedResource = injector.getInstance(ProtectedResource.class);

        //Register resources
        environment.jersey().register(personResource);
        environment.jersey().register(orderResource);
        environment.jersey().register(versionResource);
        environment.jersey().register(protectedResource);

        //Jersey link filter
        //environment.jersey().property(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, LinkFilter.class);
        addCorsHeaders(environment);
        //Add healthcheck
        environment.healthChecks().register("Test Health Check", new TestDataHealthCheck());
    }

    // you probably would like to move this method to separate class
    private Injector createInjector(final ServerConfiguration conf) {
        return Guice.createInjector(
                new MultipleInstancesModule(),
                new MongoModule(mongoBundle),
                new DAOModule(),
                new ProviderModule());
    }

    private void setupAuthConfig(Environment environment) {
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new BasicAuthenticator())
                .setAuthorizer(new BasicAuthorizer())
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));

        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
    }

    private void addCorsHeaders(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter("allowedOrigins", "*");    // allowed origins comma separated
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowedMethods", "GET,PUT,POST,DELETE,OPTIONS,HEAD");
        filter.setInitParameter("allowCredentials", "true");
    }
}