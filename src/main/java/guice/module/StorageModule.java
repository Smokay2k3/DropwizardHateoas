package guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import storage.BasicPersonStorage;
import storage.Storage;

/**
 * Created by timp on 13/11/2015.
 */
public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        //Should be a bit smarter how it provides storage as not all storage should be person
        bind(Storage.class).to(BasicPersonStorage.class).in(Singleton.class);
    }
}
