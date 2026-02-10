package controller;

import database.dao.ICustomerDAO;
import model.Customer;

import java.util.List;

public class CustomerController {

    private final ICustomerDAO customerDAO;

    public CustomerController(ICustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void addCustomer(Customer customer) {
        customerDAO.insertCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }
}
