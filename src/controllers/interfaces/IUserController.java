package controllers.interfaces;

public interface IUserController {
    String register(String name, String surname, String email, String phone);
    String getById(int id);
    String getAll();
    String getByPhone(String phone);
}