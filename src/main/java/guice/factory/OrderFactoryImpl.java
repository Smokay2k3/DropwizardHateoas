package guice.factory;

import com.google.inject.Inject;
import guice.bindingannotation.VerbosityConfigured;
import guice.impl.DetailLogger;
import model.Order;

/**
 * Created by timp on 16/11/2015.
 */
public class OrderFactoryImpl implements OrderFactory {

    @Inject
    @VerbosityConfigured
    private DetailLogger logger;

    @Override
    public Order create() {
        logger.log("OrderFactoryImpl create called with params");
        return new Order();
    }

    @Override
    public Order create(Long orderId, Long personId, String description) {
        logger.log("OrderFactoryImpl create called with params");
        return new Order(orderId, personId, description);
    }

    public void setLogger(DetailLogger logger) {
        this.logger = logger;
    }
}
