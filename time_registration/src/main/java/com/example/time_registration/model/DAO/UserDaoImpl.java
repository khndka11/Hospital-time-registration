package com.example.time_registration.model.DAO;

import com.example.time_registration.model.entities.User;

import java.sql.*;

public class UserDaoImpl implements UserDAO {
    private static UserDaoImpl instance = null;
    private Connection connection;

    private UserDaoImpl() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinic", "root", "123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Singleton */
    public static UserDaoImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }


    @Override
    public boolean addUser(User user) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("INSERT INTO users (username, name, phone, password, role) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public User findByUsername(String username) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phoneNumber");
                String password = resultSet.getString("password");
                User user = new User(name, username, phone, password);
                return user;
            }
            return null;
        } catch (SQLException e) {
            return null;
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? and password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String pass = resultSet.getString("password");
                User user = new User(name, username, phone, pass);
                return user;
            }
            return null;
        } catch (SQLException e) {
            return null;
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public User getUser(String username) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String password = resultSet.getString("password");
                User user = new User(name, username, phone, password);
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    private void close(Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
