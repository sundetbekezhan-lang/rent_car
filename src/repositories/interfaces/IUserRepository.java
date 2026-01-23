package repositories.interfaces;

import models.User;

import java.util.List;

public interface IUserRepository {
    boolean register(User user);
    User getById(int id);
    List<User> getAll();

}
