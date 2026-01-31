package menu;


import database.CarDAO;
import model.*;
import model.CarCategory;
import factory.RentalOrderFactory;
import model.Role;
import model.SystemUser;
import security.AccessManager;
import database.RentalOrderDAO;
import java.util.ArrayList;
import java.util.Scanner;

public class CarRentalMenu implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final CarDAO carDAO = new CarDAO();
    private final RentalOrderDAO rentalOrderDAO = new RentalOrderDAO();

    private final ArrayList<Customer> customers = new ArrayList<>();
    private final ArrayList<RentalOrder> orders = new ArrayList<>();
    private final ArrayList<Service> services = new ArrayList<>();
    private final ArrayList<Insurance> insurances = new ArrayList<>();

    @Override
    public void displayMenu() {
        System.out.println("""
                ===== CAR RENTAL SYSTEM =====
                1. Add car
                2. View cars
                3. Add customer
                4. Add service
                5. Create rental order
                6. View rental orders
                7. View full rental order (JOIN)
                0. Exit
                =============================
                """);
        System.out.print("Choose option: ");
    }

    @Override
    public void run() {
        boolean running = true;
        AccessManager.login(new SystemUser("admin", Role.ADMIN));

        while (running) {
            displayMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addCar();
                    case 2 -> viewCars();
                    case 3 -> addCustomer();
                    case 4 -> addService();
                    case 5 -> createRentalOrder();
                    case 6 -> viewOrders();
                    case 7 -> viewFullRentalOrder();
                    case 0 -> running = false;
                    default -> System.out.println("Wrong option");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private void addCar() {
        AccessManager.check(Role.ADMIN);
        System.out.print("Car ID: ");

        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Brand: ");
        String brand = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Price per day: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Choose category: ECONOMY, SUV, LUXURY");
        CarCategory category = CarCategory.valueOf(scanner.nextLine().toUpperCase());

        Car car = new Car(id, brand, model, year, price, category);
        carDAO.insertCar(car);

        System.out.println("Car saved to database");
    }

    private void viewCars() {
        for (Car car : carDAO.getAllCars()) {
            System.out.println(car.getInfo());
        }
    }

    private void addCustomer() {
        System.out.print("Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        customers.add(new Customer(id, name, phone));
        System.out.println("Customer added");
    }

    private void addService() {
        System.out.print("Service name: ");
        String name = scanner.nextLine();

        System.out.print("Service price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        services.add(new Service(name, price));
        System.out.println("Service added");
    }

    private void createRentalOrder() {
        if (customers.isEmpty()) {
            System.out.println("Add customer first");
            return;
        }

        var cars = carDAO.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("No cars in database");
            return;
        }

        System.out.println("Choose customer:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(i + ": " + customers.get(i).getInfo());
        }
        int cIndex = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Choose car:");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(i + ": " + cars.get(i).getInfo());
        }
        int carIndex = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Days: ");
        int days = scanner.nextInt();
        scanner.nextLine();

        RentalOrder order = RentalOrderFactory.create(
                customers.get(cIndex),
                cars.get(carIndex),
                days
        );

        if (!services.isEmpty()) {
            System.out.println("Add service? (yes/no)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                for (int i = 0; i < services.size(); i++) {
                    System.out.println(i + ": " + services.get(i));
                }
                int sIndex = scanner.nextInt();
                scanner.nextLine();
                order.addService(services.get(sIndex));
            }
        }

        orders.add(order);
        System.out.println("Rental order created");
    }

    private void viewOrders() {
        for (RentalOrder order : orders) {
            System.out.println(order);
        }
    }
    private void viewExpensiveCars() {
        System.out.print("Min price per day: ");
        double minPrice = scanner.nextDouble();
        scanner.nextLine();

        carDAO.getAllCars().stream()
                .filter(c -> c.getPricePerDay() >= minPrice)
                .forEach(c -> System.out.println(c.getInfo()));
    }
    private void viewFullRentalOrder() {
        System.out.print("Enter order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();

        var order = rentalOrderDAO.getFullRentalOrder(orderId);

        if (order != null) {
            System.out.println("=== FULL RENTAL ORDER (JOIN) ===");
            System.out.println(order);
        } else {
            System.out.println("Order not found");
        }
    }
}