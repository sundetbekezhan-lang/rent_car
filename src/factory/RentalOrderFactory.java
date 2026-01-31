package factory;

import model.Car;
import model.Customer;
import model.RentalOrder;

public class RentalOrderFactory {

    private RentalOrderFactory() {
        // private constructor
    }

    public static RentalOrder create(Customer customer, Car car, int days) {
        return new RentalOrder(customer, car, days);
    }
}
