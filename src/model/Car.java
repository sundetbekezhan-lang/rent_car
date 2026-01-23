package model;

public class Car extends RentEntity {

    private String brand;
    private String model;
    private int year;
    private double pricePerDay;

    public Car(int id, String brand, String model, int year, double pricePerDay) {
        super(id);
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    @Override
    public String getInfo() {
        return id + " | " + brand + " " + model +
                " | year: " + year +
                " | price/day: " + pricePerDay;
    }
}
