import controller.*;
import database.*;
import database.dao.*;
import menu.CarRentalMenu;
import menu.Menu;
import model.SystemUser;
import security.AccessManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        // Initialize DAOs
        IUserDAO userDAO = new UserDAO();
        ICarDAO carDAO = new CarDAO();
        ICustomerDAO customerDAO = new CustomerDAO();
        IServiceDAO serviceDAO = new ServiceDAO();
        IRentalOrderDAO rentalOrderDAO = new RentalOrderDAO();
        
        // Initialize Controllers with DAOs
        UserController userController = new UserController(userDAO);
        CarController carController = new CarController(carDAO);
        CustomerController customerController = new CustomerController(customerDAO);
        ServiceController serviceController = new ServiceController(serviceDAO);
        RentalOrderController rentalOrderController = new RentalOrderController(rentalOrderDAO);

        System.out.println("=== CAR RENTAL SYSTEM ===");
        System.out.println("1. Login");
        System.out.println("2. Register (Customer only)");
        System.out.print("Choose option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        SystemUser user = null;

        if (choice == 1) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            user = userController.login(username, password);

            if (user == null) {
                System.out.println("Wrong login or password");
                return;
            }

        } else if (choice == 2) {
            System.out.print("Choose username: ");
            String username = scanner.nextLine();

            System.out.print("Choose password: ");
            String password = scanner.nextLine();

            try {
                boolean success = userController.registerCustomer(username, password);
                if (success) {
                    System.out.println("Registration successful. Please login.");
                    user = userController.login(username, password);
                }
            } catch (Exception e) {
                System.out.println("Registration failed: " + e.getMessage());
                return;
            }

        } else {
            System.out.println("Wrong option");
            return;
        }

        AccessManager.login(user);

        Menu menu = new CarRentalMenu(
                carController,
                customerController,
                serviceController,
                rentalOrderController,
                userController
        );
        menu.run();
    }
}
