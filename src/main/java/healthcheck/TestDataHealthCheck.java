package healthcheck;

import com.codahale.metrics.health.HealthCheck;


public class TestDataHealthCheck extends HealthCheck {

    //Always healthy for now
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
