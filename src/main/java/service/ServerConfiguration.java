package service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meltmedia.dropwizard.mongo.MongoConfiguration;
import io.dropwizard.Configuration;

/**
 * Created by timp on 13/11/2015.
 */
public class ServerConfiguration extends Configuration {

    @JsonProperty
    protected MongoConfiguration mongo;

    public MongoConfiguration getMongo() {
        return mongo;
    }
}
