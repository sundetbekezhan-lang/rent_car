package controller;

import database.dao.ICarDAO;
import model.Car;

import java.util.List;

public class CarController {

    private final ICarDAO carDAO;

    public CarController(ICarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public void addCar(Car car) {
        carDAO.insertCar(car);
    }

    public List<Car> getAllCars() {
        return carDAO.getAllCars();
    }
}
