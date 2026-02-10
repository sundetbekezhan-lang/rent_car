package database.dao;

import model.Customer;

import java.util.List;

public interface ICustomerDAO {
    void insertCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(int id);
}
