import controllers.interfaces.IUserController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IUserController controller;

    public MyApplication(IUserController controller) {
        this.controller = controller;
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to Rent Car Application");
        System.out.println("Select option:");
        System.out.println("1. List all clients");
        System.out.println("2. Find client by ID");
        System.out.println("3. Register new client");
        System.out.println("4. Find client by phone");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter option (0-4): ");
    }

    public void start() {
        while (true) {
            mainMenu();
            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1: getAllUsersMenu(); break;
                    case 2: getUserByIdMenu(); break;
                    case 3: registerUserMenu(); break;
                    case 4: getUserByPhoneMenu(); break;
                    case 0: return;
                    default: System.out.println("Invalid option, try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("*************************");
        }
    }

    private void getAllUsersMenu() {
        String response = controller.getAll();
        System.out.println(response);
    }

    private void getUserByIdMenu() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        String response = controller.getById(id);
        System.out.println(response);
    }

    private void registerUserMenu() {
        System.out.println("Enter name:");
        String name = scanner.next();
        System.out.println("Enter surname:");
        String surname = scanner.next();
        System.out.println("Enter email:");
        String email = scanner.next();
        System.out.println("Enter phone number:");
        String phone = scanner.next();

        String response = controller.register(name, surname, email, phone);
        System.out.println(response);
    }

    private void getUserByPhoneMenu() {
        System.out.println("Enter phone number:");
        String phone = scanner.next();
        String response = controller.getByPhone(phone);
        System.out.println(response);
    }
}