package guice.impl;

/**
 * Created by timp on 13/11/2015.
 */
public class DefaultLogger implements DetailLogger {

    @Override
    public void log(String details) {
        System.out.println("<< DefaultLogger >> " + details);
    }
}
