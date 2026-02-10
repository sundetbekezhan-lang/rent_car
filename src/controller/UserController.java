package controller;

import database.dao.IUserDAO;
import model.Customer;
import model.SystemUser;

import java.util.List;

public class UserController {

    private final IUserDAO userDAO;

    public UserController(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public SystemUser login(String username, String password) {
        return userDAO.login(username, password);
    }

    public boolean registerCustomer(String username, String password) {
        return userDAO.registerCustomer(username, password);
    }

    public List<Customer> getAllCustomers() {
        return userDAO.getAllCustomers();
    }

    public Customer getCustomerByUsername(String username) {
        return userDAO.getCustomerByUsername(username);
    }
}
