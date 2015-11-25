package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.google.inject.Inject;
import guice.impl.DetailLogger;

@Path("/api")
@Produces(MediaType.TEXT_PLAIN)
public class VersionResource {

    public static final String VERSION = "v1";

    public int exceptionMeteringCount = 0;

    @Inject
    private DetailLogger logger1;

    @Inject
    private DetailLogger logger2;

    @GET
    public String get() {
        logger1.log("Version requested");
        logger2.log(String.format("About to return version to user [%s]", VERSION));
        return VERSION;
    }

    @Metered
    @ExceptionMetered
    @GET
    @Path("/testExceptionMetering")
    public String testExceptionMetering() {
        logger1.log("Exception Metering Called");

        exceptionMeteringCount++;

        if((exceptionMeteringCount % 3) == 0){
            throw new ServerErrorException(666);
        }

        return "Exception Metering Called";
    }

    public void setLogger1(DetailLogger logger1) {
        this.logger1 = logger1;
    }

    public void setLogger2(DetailLogger logger2) {
        this.logger2 = logger2;
    }
}