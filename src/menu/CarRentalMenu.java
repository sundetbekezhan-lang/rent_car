package menu;


import database.CarDAO;
import model.*;
import model.CarCategory;
import factory.RentalOrderFactory;
import model.Role;
import security.AccessManager;
import database.RentalOrderDAO;
import java.util.ArrayList;
import java.util.Scanner;
import database.UserDAO;

public class CarRentalMenu implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final CarDAO carDAO = new CarDAO();
    private final RentalOrderDAO rentalOrderDAO = new RentalOrderDAO();

    private final ArrayList<Customer> customers = new ArrayList<>();
    private final ArrayList<RentalOrder> orders = new ArrayList<>();
    private final ArrayList<Service> services = new ArrayList<>();
    private final ArrayList<Insurance> insurances = new ArrayList<>();
    private final UserDAO userDAO = new UserDAO();

    @Override
    public void displayMenu() {
        System.out.println("===== CAR RENTAL SYSTEM =====");

        Role role = security.AccessManager.getCurrentRole();

        // Доступно всем
        System.out.println("2. View cars");

        // MANAGER и ADMIN
        if (role == Role.MANAGER || role == Role.ADMIN) {
            System.out.println("3. Add customer");
            System.out.println("4. Add service");
            System.out.println("5. Create rental order");
            System.out.println("6. View rental orders");
        }

        // Только ADMIN
        if (role == Role.ADMIN) {
            System.out.println("1. Add car");
            System.out.println("7. View full rental order (JOIN)");
            System.out.println("8. View all customers");
        }

        System.out.println("0. Exit");
        System.out.println("=============================");
        System.out.print("Choose option: ");
    }

    @Override
    public void run() {
        boolean running = true;


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
                    case 8 -> viewAllCustomers();
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

        Car car = new Car(1, brand, model, year, price, category); // id не важен
        carDAO.insertCar(car);

        System.out.println("Car saved to database");
    }

    private void viewCars() {
        for (Car car : carDAO.getAllCars()) {
            System.out.println(car.getInfo());
        }
    }

    private void addCustomer() {
        AccessManager.check(Role.MANAGER);
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

        AccessManager.check(Role.MANAGER);

        System.out.print("Service name: ");
        String name = scanner.nextLine();

        System.out.print("Service price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        services.add(new Service(name, price));
        System.out.println("Service added");
    }

    private void createRentalOrder() {
        AccessManager.check(Role.MANAGER);
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
        AccessManager.check(Role.MANAGER);
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
        AccessManager.check(Role.ADMIN);
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
    private void viewAllCustomers() {
        AccessManager.check(Role.ADMIN);
        userDAO.printAllCustomers();
    }
}