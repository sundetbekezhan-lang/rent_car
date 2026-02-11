package factory;

import model.Car;
import model.Customer;
import model.RentalOrder;
import model.Service;

import java.util.List;

/**
 * Factory для создания объектов RentalOrder с различными бизнес-правилами и валидацией.
 * Упрощает создание заказов и обеспечивает единообразную логику создания.
 */
public class RentalOrderFactory {

    private static final int MIN_RENTAL_DAYS = 1;
    private static final int MAX_RENTAL_DAYS = 365;

    private RentalOrderFactory() {
        // Utility class - не должен быть инстанцирован
    }

    /**
     * Создает стандартный заказ аренды с валидацией.
     */
    public static RentalOrder createStandardOrder(Customer customer, Car car, int days) {
        validateOrderParameters(customer, car, days);
        return new RentalOrder(customer, car, days);
    }

    /**
     * Создает заказ аренды с дополнительными услугами.
     */
    public static RentalOrder createOrderWithServices(Customer customer, Car car, int days, List<Service> services) {
        validateOrderParameters(customer, car, days);
        RentalOrder order = new RentalOrder(customer, car, days);
        
        if (services != null) {
            for (Service service : services) {
                if (service != null) {
                    order.addService(service);
                }
            }
        }
        
        return order;
    }

    /**
     * Создает срочный заказ (минимальный срок аренды).
     */
    public static RentalOrder createUrgentOrder(Customer customer, Car car) {
        validateOrderParameters(customer, car, MIN_RENTAL_DAYS);
        return new RentalOrder(customer, car, MIN_RENTAL_DAYS);
    }

    /**
     * Создает долгосрочный заказ с валидацией максимального срока.
     */
    public static RentalOrder createLongTermOrder(Customer customer, Car car, int days) {
        if (days > MAX_RENTAL_DAYS) {
            throw new IllegalArgumentException(
                String.format("Long-term rental cannot exceed %d days", MAX_RENTAL_DAYS)
            );
        }
        validateOrderParameters(customer, car, days);
        return new RentalOrder(customer, car, days);
    }

    /**
     * Валидация параметров заказа перед созданием.
     */
    private static void validateOrderParameters(Customer customer, Car car, int days) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }

        if (days < MIN_RENTAL_DAYS) {
            throw new IllegalArgumentException(
                String.format("Rental days must be at least %d", MIN_RENTAL_DAYS)
            );
        }
        if (days > MAX_RENTAL_DAYS) {
            throw new IllegalArgumentException(
                String.format("Rental days cannot exceed %d", MAX_RENTAL_DAYS)
            );
        }
    }
}
