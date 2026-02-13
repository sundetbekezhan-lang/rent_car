package model;


import java.util.ArrayList;
import java.util.List;

public class RentalOrder {

    private Customer customer;
    private Car car;
    private int days;
    private int orderId;
    private List<Service> services;


    public RentalOrder(Customer customer, Car car, int days) {
        this.customer = customer;
        this.car = car;
        this.services = new ArrayList<>();
        setDays(days);
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public int getDays() {
        return days;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Service> getServices() {
        return services;
    }

    public double getTotalPrice() {
        return car.getPricePerDay() * days +
                services.stream()
                        .mapToDouble(Service::getPrice)
                        .sum();
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
    public boolean hasService(String serviceName) {
        return services.stream()
                .anyMatch(s -> s.toString().toLowerCase().contains(serviceName.toLowerCase()));
    }

    public void setTotalPrice(double total) {
    }
}
