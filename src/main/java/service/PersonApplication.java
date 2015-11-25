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
import guice.module.ProviderModule;
import guice.module.MultipleInstancesModule;
import guice.module.StorageModule;
import guice.providers.OrderBuilderProvider;
import guice.providers.PersonBuilderProvider;
import guice.providers.PersonBuilderProviderImpl;
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

    private void populateWithTestData(Injector injector, PersonResource personResource) {
        PersonBuilderProvider provider = injector.getInstance(PersonBuilderProvider.class);
        personResource.add(provider.get()
                .withFirstName("Bill").withLastName("Clinton")
                .withContactInfo(new ContactInfo("123 Broadway St", "bill@clinton.gov"))
                .build());

        personResource.add(provider.get()
                .withFirstName("George").withLastName("Bush")
                .withContactInfo( new ContactInfo("2140 6th Ave", "george@bush.gov"))
                .build());
    }

    private void populateWithTestData(Injector injector, OrderResource orderResource) {
        OrderBuilderProvider provider = injector.getInstance(OrderBuilderProvider.class);

        orderResource.add(provider.get()
                .withPersonId(0l).withDescription("New christmas socks").build());
        orderResource.add(provider.get()
                .withPersonId(0l).withDescription("Chainsaw").build());
        orderResource.add(provider.get()
                .withPersonId(0l).withDescription("Gadget").build());
        orderResource.add(provider.get()
                .withPersonId(1l).withDescription("A good pair of boots").build());
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