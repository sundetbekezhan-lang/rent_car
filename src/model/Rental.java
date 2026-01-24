package model;

public class Rental {

    private Customer customer;
    private Car car;
    private int days;

    public Rental(Customer customer, Car car, int days) {
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

    public double getTotalPrice() {
        return days * car.getPricePerDay();
    }

    @Override
    public String toString() {
        return "Rental{" +
                "customer=" + customer.getInfo() +
                ", car=" + car.getInfo() +
                ", days=" + days +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
