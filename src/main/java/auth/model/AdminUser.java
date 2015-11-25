package auth.model;

/**
 * Created by timp on 12/11/2015.
 */
@AuthRole(Role.ADMIN)
public class AdminUser extends User {

    public AdminUser(String username) {
        super(username);
    }
}
