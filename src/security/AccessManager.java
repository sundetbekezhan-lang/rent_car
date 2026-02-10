package security;

import model.Role;
import model.SystemUser;

public class AccessManager {

    private static SystemUser currentUser;

    public static void login(SystemUser user) {
        currentUser = user;
    }

    public static void check(Role requiredRole) {
        if (currentUser == null) {
            throw new SecurityException("Not logged in");
        }

        if (currentUser.getRole().ordinal() > requiredRole.ordinal()) {
            throw new SecurityException("Access denied");
        }
    }

    public static Role getCurrentRole() {
        if (currentUser == null) {
            return null;
        }
        return currentUser.getRole();
    }

    public static SystemUser getCurrentUser() {
        return currentUser;
    }
}