package guice.factory;

import model.Order;

/**
 * Created by timp on 16/11/2015.
 */
public interface OrderFactory {
    Order create();

    Order create(Long orderId, Long personId, String description);
}
