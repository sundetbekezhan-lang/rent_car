package database;

import model.Role;
import model.SystemUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // ===== LOGIN =====
    public SystemUser login(String username, String password) {

        // базовая валидация
        if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {
            return null;
        }

        String sql = """
            SELECT username, role
            FROM users
            WHERE username = ? AND password = ?
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new SystemUser(
                        rs.getString("username"),
                        Role.valueOf(rs.getString("role"))
                );
            }

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    // ===== REGISTRATION (CUSTOMER ONLY) =====
    public boolean registerCustomer(String username, String password) {

        // базовая валидация
        if (username == null || username.isBlank()
                || username.length() < 3
                || username.contains(" ")) {

            System.out.println("Username must be at least 3 characters and without spaces");
            return false;
        }

        if (password == null || password.isBlank()
                || password.length() < 4) {

            System.out.println("Password must be at least 4 characters");
            return false;
        }

        String sql = """
            INSERT INTO users (username, password, role)
            VALUES (?, ?, 'USER')
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Register error: " + e.getMessage());
            return false;
        }


    }
    public void printAllCustomers() {

        String sql = """
        SELECT username
        FROM users
        WHERE role = 'USER'
    """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("=== CUSTOMERS ===");

            while (rs.next()) {
                System.out.println("- " + rs.getString("username"));
            }

        } catch (Exception e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }
}
