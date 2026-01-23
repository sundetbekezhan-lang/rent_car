package repositories;

import data.interfaces.IDB;
import models.User;
import repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;  // Dependency Injection

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean register(User user) {
        Connection con = null;

        try {
            con = db.getConnection();
            String sql = "INSERT INTO users(name,surname,gender) VALUES (?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getSurname());
            st.setBoolean(3, user.getGender());

            st.execute();

            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public User getById(int id) {
        Connection con = null;

        try {
            con = db.getConnection();
            String sql = "SELECT id,name,surname,gender FROM users WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<User> getAll() {
        Connection con = null;

        try {
            con = db.getConnection();
            String sql = "SELECT id,name,surname,gender FROM users";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"));

                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return null;
    }
}
@Override
public List<User> getByName(String name) {
    List<User> users = new ArrayList<>();

    try (Connection con = db.getConnection()) {
        String sql = "SELECT id, name, surname, gender FROM users WHERE name=?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, name);

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            users.add(new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getBoolean("gender")
            ));
        }
    } catch (SQLException e) {
        System.out.println("sql error: " + e.getMessage());
    }

    return users;
}
@Override
public boolean update(User user) {
    try (Connection con = db.getConnection()) {
        String sql = "UPDATE users SET name=?, surname=?, gender=? WHERE id=?";
        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, user.getName());
        st.setString(2, user.getSurname());
        st.setBoolean(3, user.getGender());
        st.setInt(4, user.getId());

        return st.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("sql error: " + e.getMessage());
    }
    return false;
}
@Override
public boolean deleteById(int id) {
    try (Connection con = db.getConnection()) {
        String sql = "DELETE FROM users WHERE id=?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, id);

        return st.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("sql error: " + e.getMessage());
    }
    return false;
}
