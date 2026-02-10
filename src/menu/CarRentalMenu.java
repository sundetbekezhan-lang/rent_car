package menu;

import controller.*;
import factory.RentalOrderFactory;
import model.*;
import model.CarCategory;
import model.Role;
import security.AccessManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarRentalMenu implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final CarController carController;
    private final CustomerController customerController;
    private final ServiceController serviceController;
    private final RentalOrderController rentalOrderController;
    private final UserController userController;

    public CarRentalMenu(CarController carController,
                         CustomerController customerController,
                         ServiceController serviceController,
                         RentalOrderController rentalOrderController,
                         UserController userController) {
        this.carController = carController;
        this.customerController = customerController;
        this.serviceController = serviceController;
        this.rentalOrderController = rentalOrderController;
        this.userController = userController;
    }

    private boolean hasManagerOrAdminAccess() {
        Role currentRole = AccessManager.getCurrentRole();
        return currentRole == Role.MANAGER || currentRole == Role.ADMIN;
    }

    private void handleCase3() {
        Role role = AccessManager.getCurrentRole();
        if (role == Role.USER) {
            createMyRentalOrder();
        } else {
            addCustomer();
        }
    }

    private void handleCase4() {
        Role role = AccessManager.getCurrentRole();
        if (role == Role.USER) {
            viewMyOrders();
        } else {
            addService();
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("===== CAR RENTAL SYSTEM =====");

        Role role = AccessManager.getCurrentRole();

        // Доступно всем
        System.out.println("2. View cars");

        // USER может создавать свои заказы
        if (role == Role.USER) {
            System.out.println("3. Create my rental order");
            System.out.println("4. View my rental orders");
        }

        // MANAGER и ADMIN
        if (role == Role.MANAGER || role == Role.ADMIN) {
            System.out.println("5. Add customer");
            System.out.println("6. Add service");
            System.out.println("7. Create rental order (for any customer)");
            System.out.println("8. View all rental orders");
        }

        // Только ADMIN
        if (role == Role.ADMIN) {
            System.out.println("1. Add car");
            System.out.println("9. View full rental order (JOIN)");
            System.out.println("10. View all customers");
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
                    case 3 -> handleCase3();
                    case 4 -> handleCase4();
                    case 5 -> addCustomer();
                    case 6 -> addService();
                    case 7 -> createRentalOrder();
                    case 8 -> viewOrders();
                    case 9 -> viewFullRentalOrder();
                    case 10 -> viewAllCustomers();
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

        Car car = new Car(1, brand, model, year, price, category);
        carController.addCar(car);

        System.out.println("Car saved to database");
    }

    private void viewCars() {
        List<Car> cars = carController.getAllCars();
        for (Car car : cars) {
            System.out.println(car.getInfo());
        }
    }

    private void addCustomer() {
        if (!hasManagerOrAdminAccess()) {
            System.out.println("Access denied");
            return;
        }
        
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            boolean success = userController.registerCustomer(username, password);
            if (success) {
                System.out.println("Customer (user) added successfully to users table");
            } else {
                System.out.println("Failed to add customer");
            }
        } catch (Exception e) {
            System.out.println("Error adding customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addService() {
        if (!hasManagerOrAdminAccess()) {
            System.out.println("Access denied");
            return;
        }

        try {
            System.out.print("Service name: ");
            String name = scanner.nextLine();

            System.out.print("Service price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            Service service = new Service(name, price);
            serviceController.addService(service);
            System.out.println("Service added");
        } catch (Exception e) {
            System.out.println("Error adding service: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createRentalOrder() {
        if (!hasManagerOrAdminAccess()) {
            System.out.println("Access denied");
            return;
        }
        
        try {
            List<Customer> customers = customerController.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("Add customer first");
            return;
        }

        List<Car> cars = carController.getAllCars();
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

        List<Service> selectedServices = null;
        List<Service> availableServices = serviceController.getAllServices();
        
        if (!availableServices.isEmpty()) {
            System.out.println("Add service? (yes/no)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                selectedServices = new ArrayList<>();
                System.out.println("Available services:");
                for (int i = 0; i < availableServices.size(); i++) {
                    System.out.println(i + ": " + availableServices.get(i));
                }
                System.out.print("Enter service number (or -1 to finish): ");
                while (true) {
                    int sIndex = scanner.nextInt();
                    scanner.nextLine();
                    if (sIndex == -1) {
                        break;
                    }
                    if (sIndex >= 0 && sIndex < availableServices.size()) {
                        selectedServices.add(availableServices.get(sIndex));
                        System.out.println("Service added. Enter another service number or -1 to finish: ");
                    } else {
                        System.out.println("Invalid service number. Try again or -1 to finish: ");
                    }
                }
            }
        }

        // Используем Factory для создания заказа с услугами
        RentalOrder order;
        if (selectedServices != null && !selectedServices.isEmpty()) {
            order = RentalOrderFactory.createOrderWithServices(
                    customers.get(cIndex),
                    cars.get(carIndex),
                    days,
                    selectedServices
            );
        } else {
            order = RentalOrderFactory.createStandardOrder(
                    customers.get(cIndex),
                    cars.get(carIndex),
                    days
            );
        }

            rentalOrderController.createRentalOrder(order);
            System.out.println("Rental order created");
        } catch (Exception e) {
            System.out.println("Error creating rental order: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void viewOrders() {
        if (!hasManagerOrAdminAccess()) {
            System.out.println("Access denied");
            return;
        }
        
        try {
            List<Integer> orderIds = rentalOrderController.getAllOrderIds();
            
            if (orderIds.isEmpty()) {
                System.out.println("No rental orders found in database");
                return;
            }
            
            System.out.println("=== AVAILABLE RENTAL ORDER IDs ===");
            for (Integer orderId : orderIds) {
                System.out.println("- Order ID: " + orderId);
            }
            
            System.out.print("\nEnter order ID to view details (or 0 to cancel): ");
            int selectedOrderId = scanner.nextInt();
            scanner.nextLine();
            
            if (selectedOrderId == 0) {
                return;
            }
            
            RentalOrder order = rentalOrderController.getFullRentalOrder(selectedOrderId);
            
            if (order != null) {
                System.out.println("\n=== RENTAL ORDER DETAILS ===");
                System.out.println(order);
            } else {
                System.out.println("Order with ID " + selectedOrderId + " not found");
            }
            
        } catch (Exception e) {
            System.out.println("Error loading orders: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void viewFullRentalOrder() {
        AccessManager.check(Role.ADMIN);
        System.out.print("Enter order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();

        RentalOrder order = rentalOrderController.getFullRentalOrder(orderId);

        if (order != null) {
            System.out.println("=== FULL RENTAL ORDER (JOIN) ===");
            System.out.println(order);
        } else {
            System.out.println("Order not found");
        }
    }

    private void viewAllCustomers() {
        AccessManager.check(Role.ADMIN);
        try {
            List<Customer> customers = userController.getAllCustomers();
            System.out.println("=== REGISTERED USERS ===");
            if (customers.isEmpty()) {
                System.out.println("No users found in database");
            } else {
                for (Customer customer : customers) {
                    System.out.println("- " + customer.getInfo());
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading customers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createMyRentalOrder() {
        try {
            // Получаем текущего пользователя
            model.SystemUser currentUser = AccessManager.getCurrentUser();
            if (currentUser == null) {
                System.out.println("You must be logged in");
                return;
            }

            // Получаем или создаем Customer из текущего пользователя
            Customer myCustomer = userController.getCustomerByUsername(currentUser.getUsername());
            if (myCustomer == null) {
                System.out.println("Error: Customer profile not found");
                return;
            }

            // Если customer_id = 0, значит клиента нет в таблице customer, создаем его
            if (myCustomer.getId() == 0) {
                // Создаем клиента в таблице customer
                customerController.addCustomer(myCustomer);
                // Получаем обновленного клиента с реальным ID
                // Для упрощения используем имя как идентификатор
                List<Customer> allCustomers = customerController.getAllCustomers();
                myCustomer = allCustomers.stream()
                        .filter(c -> c.getName().equals(currentUser.getUsername()))
                        .findFirst()
                        .orElse(myCustomer);
            }

            System.out.println("\n=== CREATE YOUR RENTAL ORDER ===");
            System.out.println("Customer: " + myCustomer.getInfo());

            List<Car> cars = carController.getAllCars();
            if (cars.isEmpty()) {
                System.out.println("No cars available in database");
                return;
            }

            System.out.println("\nAvailable cars:");
            for (int i = 0; i < cars.size(); i++) {
                System.out.println(i + ": " + cars.get(i).getInfo());
            }
            System.out.print("Choose car number: ");
            int carIndex = scanner.nextInt();
            scanner.nextLine();

            if (carIndex < 0 || carIndex >= cars.size()) {
                System.out.println("Invalid car selection");
                return;
            }

            System.out.print("Enter rental days: ");
            int days = scanner.nextInt();
            scanner.nextLine();

            List<Service> selectedServices = null;
            List<Service> availableServices = serviceController.getAllServices();

            if (!availableServices.isEmpty()) {
                System.out.println("\nAdd services? (yes/no)");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    selectedServices = new ArrayList<>();
                    System.out.println("Available services:");
                    for (int i = 0; i < availableServices.size(); i++) {
                        System.out.println(i + ": " + availableServices.get(i));
                    }
                    System.out.print("Enter service number (or -1 to finish): ");
                    while (true) {
                        int sIndex = scanner.nextInt();
                        scanner.nextLine();
                        if (sIndex == -1) {
                            break;
                        }
                        if (sIndex >= 0 && sIndex < availableServices.size()) {
                            selectedServices.add(availableServices.get(sIndex));
                            System.out.println("Service added. Enter another service number or -1 to finish: ");
                        } else {
                            System.out.println("Invalid service number. Try again or -1 to finish: ");
                        }
                    }
                }
            }

            // Используем Factory для создания заказа
            RentalOrder order;
            if (selectedServices != null && !selectedServices.isEmpty()) {
                order = RentalOrderFactory.createOrderWithServices(
                        myCustomer,
                        cars.get(carIndex),
                        days,
                        selectedServices
                );
            } else {
                order = RentalOrderFactory.createStandardOrder(
                        myCustomer,
                        cars.get(carIndex),
                        days
                );
            }

            rentalOrderController.createRentalOrder(order);
            System.out.println("\n✓ Your rental order has been created successfully!");
            System.out.println("Order details: " + order);

        } catch (Exception e) {
            System.out.println("Error creating rental order: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void viewMyOrders() {
        try {
            model.SystemUser currentUser = AccessManager.getCurrentUser();
            if (currentUser == null) {
                System.out.println("You must be logged in");
                return;
            }

            // Получаем все заказы
            List<Integer> allOrderIds = rentalOrderController.getAllOrderIds();

            if (allOrderIds.isEmpty()) {
                System.out.println("No rental orders found");
                return;
            }

            System.out.println("\n=== YOUR RENTAL ORDERS ===");
            System.out.println("Your username: " + currentUser.getUsername());

            // Показываем все заказы (в реальном приложении можно фильтровать по customer_id)
            // Для упрощения показываем все заказы
            boolean foundOrders = false;
            for (Integer orderId : allOrderIds) {
                RentalOrder order = rentalOrderController.getFullRentalOrder(orderId);
                if (order != null && order.getCustomer().getName().equals(currentUser.getUsername())) {
                    System.out.println("\n--- Order ID: " + orderId + " ---");
                    System.out.println(order);
                    foundOrders = true;
                }
            }

            if (!foundOrders) {
                System.out.println("You don't have any rental orders yet.");
            }

        } catch (Exception e) {
            System.out.println("Error loading your orders: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
