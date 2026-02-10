package database.dao;

import model.Service;

import java.util.List;

public interface IServiceDAO {
    void insertService(Service service);
    List<Service> getAllServices();
    Service getServiceById(int id);
}
