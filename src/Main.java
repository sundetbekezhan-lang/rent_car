import database.UserDAO;
import menu.CarRentalMenu;
import menu.Menu;
import model.SystemUser;
import security.AccessManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

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

            user = userDAO.login(username, password);

            if (user == null) {
                System.out.println("Wrong login or password");
                return;
            }

        } else if (choice == 2) {
            System.out.print("Choose username: ");
            String username = scanner.nextLine();

            System.out.print("Choose password: ");
            String password = scanner.nextLine();

            boolean success = userDAO.registerCustomer(username, password);

            if (!success) {
                System.out.println("Registration failed");
                return;
            }

            System.out.println("Registration successful. Please login.");
            user = userDAO.login(username, password);

        } else {
            System.out.println("Wrong option");
            return;
        }

        AccessManager.login(user);

        Menu menu = new CarRentalMenu();
        menu.run();
    }
}
