package database;

import database.dao.IUserDAO;
import database.exception.ValidationException;
import model.Customer;
import model.Role;
import model.SystemUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    // ===== LOGIN =====
    @Override
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

        try (Connection con = DatabaseConnection.getInstance().getConnection();
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
            throw new RuntimeException("Login error: " + e.getMessage(), e);
        }
        return null;
    }
    

    // ===== REGISTRATION (CUSTOMER ONLY) =====
    @Override
    public boolean registerCustomer(String username, String password) {

        // базовая валидация
        if (username == null || username.isBlank()) {
            throw new ValidationException("Username cannot be empty");
        }
        
        if (username.length() < 3) {
            throw new ValidationException("Username must be at least 3 characters");
        }
        
        if (username.contains(" ")) {
            throw new ValidationException("Username cannot contain spaces");
        }

        if (password == null || password.isBlank()) {
            throw new ValidationException("Password cannot be empty");
        }
        
        if (password.length() < 4) {
            throw new ValidationException("Password must be at least 4 characters");
        }

        String sql = """
            INSERT INTO users (username, password, role)
            VALUES (?, ?, 'USER')
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Register error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = """
            SELECT username, role
            FROM users
            ORDER BY username
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int id = 1;
            while (rs.next()) {
                // Преобразуем пользователя из таблицы users в Customer
                // Используем username как имя, и username как телефон (или можно оставить пустым)
                customers.add(new Customer(
                        id++,
                        rs.getString("username"),
                        "N/A" // Телефон не хранится в таблице users
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading customers: " + e.getMessage(), e);
        }
        return customers;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        String sql = """
            SELECT username, role
            FROM users
            WHERE username = ?
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Создаем Customer из username (используем username как имя)
                return new Customer(
                        0, // ID не важен для создания заказа
                        rs.getString("username"),
                        "N/A" // Телефон не хранится в users
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading customer by username: " + e.getMessage(), e);
        }
        return null;
    }
}
