package guice.providers;

import model.Order;

public class OrderProviderImpl implements OrderProvider {

    @Override
    public Order get() {
        return new Order();
    }
}
