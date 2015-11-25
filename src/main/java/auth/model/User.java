package auth.model;

import java.security.Principal;
import java.util.UUID;

/**
 * Created by timp on 12/11/2015.
 */
public class User implements Principal {

    private final String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getId() {
        return UUID.randomUUID().toString();
    }
}
