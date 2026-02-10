package database.dao;

import model.Customer;
import model.SystemUser;

import java.util.List;

public interface IUserDAO {
    SystemUser login(String username, String password);
    boolean registerCustomer(String username, String password);
    List<Customer> getAllCustomers();
    Customer getCustomerByUsername(String username);
}
