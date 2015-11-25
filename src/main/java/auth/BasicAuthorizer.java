package auth;

import java.util.Arrays;

import auth.model.AuthRole;
import auth.model.Role;
import auth.model.User;
import io.dropwizard.auth.Authorizer;
import org.apache.commons.lang3.EnumUtils;

/**
 * Called is the resource needs authorisation.  This should be role specific
 */
public class BasicAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role) {
        //Check that the role is a known one
        if(!EnumUtils.isValidEnum(Role.class, role)){
            System.out.println("Unrecognised auth role " + role);
            return false;
        }

        //Only expect one as the values can hold multiple roles
        AuthRole authRole = user.getClass().getAnnotation(AuthRole.class);
        Role roleEnum = Role.valueOf(role);

        if(authRole != null){
            return Arrays.asList(authRole.value()).contains(roleEnum);
        }

        return false;
    }
}
