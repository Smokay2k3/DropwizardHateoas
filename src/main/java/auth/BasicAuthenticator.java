package auth;

import auth.model.AdminUser;
import auth.model.User;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Created by timp on 12/11/2015.
 */
public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {

    //TODO: this is where the user should be looked up and actually authenticated
    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if("adminsecret".equals(credentials.getPassword())){
            return Optional.of(new AdminUser(credentials.getUsername()));
        }

        if ("secret".equals(credentials.getPassword())) {
            return Optional.of(new User(credentials.getUsername()));
        }
        return Optional.absent();
    }


}
