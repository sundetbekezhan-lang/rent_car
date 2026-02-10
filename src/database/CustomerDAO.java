package database;

import database.dao.ICustomerDAO;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDAO {

    @Override
    public void insertCustomer(Customer customer) {
        String sql = """
            INSERT INTO customer (name, phone)
            VALUES (?, ?)
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(true);
            
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to insert customer - no rows affected");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error inserting customer: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = """
            SELECT customer_id, name, phone
            FROM customer
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("phone")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading customers: " + e.getMessage(), e);
        }
        return customers;
    }

    @Override
    public Customer getCustomerById(int id) {
        String sql = """
            SELECT customer_id, name, phone
            FROM customer
            WHERE customer_id = ?
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("phone")
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading customer: " + e.getMessage(), e);
        }
        return null;
    }
}
