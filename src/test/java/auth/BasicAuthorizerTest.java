package auth;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import auth.model.AuthRole;
import auth.model.Role;
import auth.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by timp on 13/11/2015.
 */
@RunWith(JUnit4.class)
public class BasicAuthorizerTest {

    private static final String USERNAME = "user1";

    private static final String UNKNOWN_ROLE = "NeverCreateThisRole";

    private BasicAuthorizer subject;

    @Before
    public void setup(){
        subject = new BasicAuthorizer();
    }

    @Test
    public void authorizeShouldReturnFalseForUserWithNoRoles() {
        //Given
        UserNoRoles userNoRoles = new UserNoRoles(USERNAME);

        //When
        boolean result = subject.authorize(userNoRoles, Role.USER.name());

        //Then
        assertThat(result, is(false));
    }

    @Test
    public void authorizeShouldReturnTrueWhenUserHasRoleSingle() {
        //Given
        UserAdminRole adminUser = new UserAdminRole(USERNAME);

        //When
        boolean result = subject.authorize(adminUser, Role.ADMIN.name());

        //Then
        assertThat(result, is(true));
    }

    @Test
    public void authorizeShouldReturnTrueWhenUserHasRoleMultiple() {
        //Given
        UserAllRoles allRoleUser = new UserAllRoles(USERNAME);

        //When
        boolean result = subject.authorize(allRoleUser, Role.USER.name());

        //Then
        assertThat(result, is(true));
    }

    @Test
    public void authorizeShouldReturnFalseWhenUnknownRoleProvided() {
        //Given
        UserNoRoles userNoRoles = new UserNoRoles(USERNAME);

        //When
        boolean result = subject.authorize(userNoRoles, UNKNOWN_ROLE);

        //Then
        assertThat(result, is(false));
    }

    @Test
    public void authorizeShouldReturnFalseAsAnnotationsShouldNotBeInherited() {
        //Given
        UserAdminRoleOnSuper userAdminRoleOnSuper = new UserAdminRoleOnSuper(USERNAME);

        //When
        boolean result = subject.authorize(userAdminRoleOnSuper, Role.ADMIN.name());

        //Then
        assertThat(result, is(false));
    }

    private class UserNoRoles extends User {
        public UserNoRoles(String username) {
            super(username);
        }
    }

    private class UserAdminRoleOnSuper extends UserAdminRole {
        public UserAdminRoleOnSuper(String username) {
            super(username);
        }
    }

    @AuthRole({Role.ADMIN})
    private class UserAdminRole extends User {
        public UserAdminRole(String username) {
            super(username);
        }
    }

    @AuthRole({Role.ADMIN, Role.USER})
    private class UserAllRoles extends User {
        public UserAllRoles(String username) {
            super(username);
        }
    }
}