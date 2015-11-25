package auth.model;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by timp on 13/11/2015.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface AuthRole {
    Role[] value();
}
