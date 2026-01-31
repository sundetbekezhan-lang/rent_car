package database;

import model.*;
import model.CarCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class RentalOrderDAO {

    public RentalOrder getFullRentalOrder(int orderId) {

        String sql = """
            SELECT
                ro.days,

                c.customer_id,
                c.name AS customer_name,
                c.phone,

                car.car_id,
                car.brand,
                car.model,
                car.year,
                car.price_per_day,
                car.category,

                s.name AS service_name,
                s.price AS service_price

            FROM rental_order ro
            JOIN customer c ON ro.customer_id = c.customer_id
            JOIN car ON ro.car_id = car.car_id
            LEFT JOIN rental_order_service ros ON ro.order_id = ros.order_id
            LEFT JOIN service s ON ros.service_id = s.service_id
            WHERE ro.order_id = ?
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            RentalOrder order = null;
            Set<String> addedServices = new HashSet<>();

            while (rs.next()) {

                if (order == null) {
                    Customer customer = new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("customer_name"),
                            rs.getString("phone")
                    );

                    Car car = new Car(
                            rs.getInt("car_id"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getDouble("price_per_day"),
                            CarCategory.valueOf(rs.getString("category"))
                    );

                    order = new RentalOrder(customer, car, rs.getInt("days"));
                }

                String serviceName = rs.getString("service_name");
                if (serviceName != null && !addedServices.contains(serviceName)) {
                    order.addService(new Service(
                            serviceName,
                            rs.getDouble("service_price")
                    ));
                    addedServices.add(serviceName);
                }
            }

            return order;

        } catch (Exception e) {
            System.out.println("JOIN error: " + e.getMessage());
            return null;
        }
    }
}