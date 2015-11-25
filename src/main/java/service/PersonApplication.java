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
import guice.factory.OrderFactory;
import guice.factory.PersonFactory;
import guice.module.FactoryModule;
import guice.module.MultipleInstancesModule;
import guice.module.StorageModule;
import healthcheck.TestDataHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import model.ContactInfo;
import model.TestTask;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import resources.OrderResource;
import resources.PersonResource;
import resources.ProtectedResource;
import resources.VersionResource;

public class PersonApplication extends Application<ServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new PersonApplication().run(new String[]{"server"});
    }

    @Override
    public void initialize(Bootstrap<ServerConfiguration> bootstrap) {

    }

    @Override
    public void run(ServerConfiguration configuration, Environment environment) throws Exception {
        ((HttpConnectorFactory) ((DefaultServerFactory) configuration.getServerFactory()).getApplicationConnectors().get(0)).setPort(9090);
        ((HttpConnectorFactory) ((DefaultServerFactory) configuration.getServerFactory()).getAdminConnectors().get(0)).setPort(9091);
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

        //Populate test data
        populateWithTestData(injector, personResource);
        populateWithTestData(injector, orderResource);

        //Jersey link filter
        //environment.jersey().property(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, LinkFilter.class);
        addCorsHeaders(environment);
        //Add healthcheck
        environment.healthChecks().register("Test Health Check", new TestDataHealthCheck());
        //Tasks
        environment.admin().addTask(new TestTask());
    }

    // you probably would like to move this method to separate class
    private Injector createInjector(final ServerConfiguration conf) {
        return Guice.createInjector(
                new MultipleInstancesModule(),
                new StorageModule(),
                new FactoryModule());
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

    private void populateWithTestData(Injector injector, PersonResource personResource) {
        PersonFactory personFactory = injector.getInstance(PersonFactory.class);

        personResource.add(personFactory.create(null, "Bill", "Clinton", new ContactInfo("123 Broadway St", "bill@clinton.gov")));
        personResource.add(personFactory.create(null, "George", "Bush", new ContactInfo("2140 6th Ave", "george@bush.gov")));
    }

    private void populateWithTestData(Injector injector, OrderResource orderResource) {
        OrderFactory orderFactory = injector.getInstance(OrderFactory.class);

        orderResource.add(orderFactory.create(20l, 0l, "New christmas socks"), 0l);
        orderResource.add(orderFactory.create(22l, 0l, "Chainsaw"), 0l);
        orderResource.add(orderFactory.create(23l, 0l, "Gadget"), 0l);
        orderResource.add(orderFactory.create(21l, 1l, "A good pair of boots"), 1l);
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