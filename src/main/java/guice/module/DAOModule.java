package guice.module;

import DAO.OrderDAO;
import DAO.OrderMongoDAO;
import DAO.PersonDAO;
import DAO.PersonMongoDAO;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import guice.bindingannotation.Advanced;
import guice.bindingannotation.Basic;
import guice.bindingannotation.VerbosityConfigured;
import guice.impl.*;

/**
 * Created by timp on 13/11/2015.
 */
public class DAOModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PersonDAO.class).to(PersonMongoDAO.class).in(Singleton.class);
        bind(OrderDAO.class).to(OrderMongoDAO.class).in(Singleton.class);
    }
}
