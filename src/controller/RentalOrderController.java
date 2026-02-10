package controller;

import database.dao.IRentalOrderDAO;
import model.RentalOrder;

import java.util.List;

public class RentalOrderController {

    private final IRentalOrderDAO rentalOrderDAO;

    public RentalOrderController(IRentalOrderDAO rentalOrderDAO) {
        this.rentalOrderDAO = rentalOrderDAO;
    }

    public RentalOrder getFullRentalOrder(int orderId) {
        return rentalOrderDAO.getFullRentalOrder(orderId);
    }

    public void createRentalOrder(RentalOrder order) {
        rentalOrderDAO.insertRentalOrder(order);
    }

    public List<Integer> getAllOrderIds() {
        return rentalOrderDAO.getAllOrderIds();
    }
}
