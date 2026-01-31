package security;

import model.Role;
import model.SystemUser;

public class AccessManager {

    private static SystemUser currentUser;

    public static void login(SystemUser user) {
        currentUser = user;
    }

    public static void check(Role requiredRole) {
        if (currentUser == null || currentUser.getRole() != requiredRole) {
            throw new SecurityException("Access denied");
        }
    }
}
