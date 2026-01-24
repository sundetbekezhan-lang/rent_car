package model;

import java.util.ArrayList;
import java.util.List;

public class RentalOrder {

    private Customer customer;
    private Car car;
    private int days;
    private List<Service> services = new ArrayList<>();

    public RentalOrder(Customer customer, Car car, int days) {
        this.customer = customer;
        this.car = car;
        setDays(days);
    }

    public void setDays(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be positive");
        }
        this.days = days;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public double getTotalPrice() {
        double total = car.getPricePerDay() * days;
        for (Service s : services) {
            total += s.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "RentalOrder{" +
                "customer=" + customer.getInfo() +
                ", car=" + car.getInfo() +
                ", days=" + days +
                ", services=" + services +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
