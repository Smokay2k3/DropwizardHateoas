package guice.providers;

import model.Order;

public class OrderBuilderProviderImpl implements OrderBuilderProvider {

    @Override
    public Order.Builder get() {
        return new Order.Builder();
    }
}
