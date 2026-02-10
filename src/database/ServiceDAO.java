package database;

import database.dao.IServiceDAO;
import model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO implements IServiceDAO {

    @Override
    public void insertService(Service service) {
        String sql = """
            INSERT INTO service (name, price)
            VALUES (?, ?)
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, service.getName());
            ps.setDouble(2, service.getPrice());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error inserting service: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String sql = """
            SELECT service_id, name, price
            FROM service
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Service service = new Service(
                        rs.getString("name"),
                        rs.getDouble("price")
                );
                services.add(service);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading services: " + e.getMessage(), e);
        }
        return services;
    }

    @Override
    public Service getServiceById(int id) {
        String sql = """
            SELECT service_id, name, price
            FROM service
            WHERE service_id = ?
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Service(
                        rs.getString("name"),
                        rs.getDouble("price")
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading service: " + e.getMessage(), e);
        }
        return null;
    }
}
