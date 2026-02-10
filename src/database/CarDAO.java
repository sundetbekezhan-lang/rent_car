package database;

import database.dao.ICarDAO;
import model.Car;
import model.CarCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO implements ICarDAO {

    @Override
    public void insertCar(Car car) {
        String sql = """
                INSERT INTO car (brand, model, year, price_per_day, category)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, car.getBrand());
            st.setString(2, car.getModel());
            st.setInt(3, car.getYear());
            st.setDouble(4, car.getPricePerDay());
            st.setString(5, car.getCategory().name());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("car_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price_per_day"),
                        CarCategory.valueOf(rs.getString("category"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error: " + e.getMessage(), e);
        }
        return cars;
    }
}
