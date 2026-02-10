package database.dao;

import model.RentalOrder;

import java.util.List;

public interface IRentalOrderDAO {
    RentalOrder getFullRentalOrder(int orderId);
    void insertRentalOrder(RentalOrder order);
    List<Integer> getAllOrderIds();
}
