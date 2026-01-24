package repositories.interfaces;

import models.User;

import java.util.List;

public interface IUserRepository {
    boolean register(User user);
    User getById(int id);
    List<User> getAll();

}
package repositories.interfaces;

import models.User;
import java.util.List;

public interface IUserRepository {

    boolean register(User user);

    User getById(int id);

    List<User> getAll();

    List<User> getByName(String name);

    boolean update(User user);

    boolean deleteById(int id);
}
