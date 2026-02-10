package controller;

import database.dao.IServiceDAO;
import model.Service;

import java.util.List;

public class ServiceController {

    private final IServiceDAO serviceDAO;

    public ServiceController(IServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public void addService(Service service) {
        serviceDAO.insertService(service);
    }

    public List<Service> getAllServices() {
        return serviceDAO.getAllServices();
    }

    public Service getServiceById(int id) {
        return serviceDAO.getServiceById(id);
    }
}
