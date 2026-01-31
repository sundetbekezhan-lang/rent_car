package model;

public class Car extends RentEntity {

    private String brand;
    private String model;
    private int year;
    private double pricePerDay;
    private CarCategory category;

    public Car(int id, String brand, String model, int year, double pricePerDay, CarCategory category) {
        super(id);
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.category = category;

    }
    public CarCategory getCategory() {
        return category;
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
                " | " + category +
                " | year: " + year +
                " | price/day: " + pricePerDay;
    }
}
