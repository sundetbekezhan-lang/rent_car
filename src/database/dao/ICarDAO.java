package database.dao;

import model.Car;

import java.util.List;

public interface ICarDAO {
    void insertCar(Car car);
    List<Car> getAllCars();
}
