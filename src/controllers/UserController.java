package controllers;

import models.User;
import controllers.interfaces.IUserController;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public String register(String name, String surname, String email, String phone) {
        User user = new User(name, surname, email, phone);
        boolean created = repo.register(user);

        return (created ? "Registration successful!" : "Registration failed!");
    }

    @Override
    public String getById(int id) {
        User user = repo.getById(id);

        return (user == null ? "User not found!" : user.toString());
    }

    @Override
    public String getAll() {
        List<User> users = repo.getAll();
        if (users == null || users.isEmpty()) return "No users found.";

        StringBuilder response = new StringBuilder();
        for (User user : users) {
            response.append(user.toString()).append("\n");
        }

        return response.toString();
    }

    @Override
    public String getByPhone(String phone) {
        User user = repo.getByPhone(phone);

        return (user == null ? "No user found with phone: " + phone : user.toString());
    }
}