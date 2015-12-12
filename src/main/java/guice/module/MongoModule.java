package guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.meltmedia.dropwizard.mongo.MongoBundle;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import guice.bindingannotation.Advanced;
import guice.bindingannotation.Basic;
import guice.bindingannotation.VerbosityConfigured;
import guice.impl.*;
import service.ServerConfiguration;

/**
 * Created by timp on 13/11/2015.
 */
public class MongoModule extends AbstractModule {

    private MongoBundle<ServerConfiguration> mongoBungle;

    public MongoModule(MongoBundle<ServerConfiguration> mongoBungle){
        this.mongoBungle = mongoBungle;
    }

    @Override
    protected void configure() {
        bind(MongoClient.class).toInstance(mongoBungle.getClient());
        bind(DB.class).toInstance(mongoBungle.getDB());
    }
}
