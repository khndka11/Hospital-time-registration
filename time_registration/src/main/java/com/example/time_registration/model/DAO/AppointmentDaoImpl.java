package com.example.time_registration.model.DAO;

import com.example.time_registration.controller.RegisterController;
import com.example.time_registration.model.entities.Appointment;
import com.example.time_registration.model.entities.Person;
import com.example.time_registration.model.entities.User;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AppointmentDaoImpl implements AppointmentDao{
    private static AppointmentDaoImpl instance = null;
    private Connection connection;

    private AppointmentDaoImpl() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinic", "root", "123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static AppointmentDaoImpl getInstance(){
        if (instance == null) {
            instance = new AppointmentDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean addAppointment(Appointment appointment){
        PreparedStatement statement = null;

        try {
            LoggerFactory.getLogger(RegisterController.class).info("addAppointment result: {}", appointment.getTime());
            Timestamp sqlTimestamp = Timestamp.valueOf(appointment.getTime());
            LoggerFactory.getLogger(RegisterController.class).info("addAppointmentDate result: {}", sqlTimestamp);

            statement = connection.prepareStatement("INSERT INTO appointments (date, status, type, user_id) VALUES (?, ?, ?, ?)");
            statement.setTimestamp(1, sqlTimestamp);
            statement.setString(2, appointment.getStatusString().toString());
            statement.setString(3, appointment.getType().toString());
            statement.setString(4, appointment.getUser().getUsername());
            int numRowsAffected = statement.executeUpdate();
            return numRowsAffected > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            close(statement, null);
        }
    }

    @Override
    public void cancelAppointment(Appointment appointment){
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("DELETE FROM appointments WHERE time = ?");
            statement.setTimestamp(1, Timestamp.valueOf(appointment.getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public List<Appointment> getAppointments() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM appointments ORDER BY time");
            resultSet = statement.executeQuery();
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                LocalDateTime time = resultSet.getTimestamp("time").toLocalDateTime();
                String username = resultSet.getString("userId");
                Person user = PersonDaoImpl.getInstance().getUser(username);
                Appointment appointment = new Appointment(time, (User) user);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public List<Appointment> getAppointmentsByUsername(String username) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM appointments where user_id = ? ORDER BY date");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            List<Appointment> appointments = new ArrayList<>();
            User user = UserDaoImpl.getInstance().getUser(username);
            LoggerFactory.getLogger(RegisterController.class).info("uesr result: {}", user.getRole());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                int id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String userId = resultSet.getString("user_id");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                LoggerFactory.getLogger(RegisterController.class).info("date result: {}", dateTime);

                Appointment appointment = new Appointment(id, dateTime, status, type, user);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(String username) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM appointments where type = ? ORDER BY date");
            statement.setString(1, "EXAMINATION");
            resultSet = statement.executeQuery();
            List<Appointment> appointments = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                int id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String userId = resultSet.getString("user_id");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                User user = UserDaoImpl.getInstance().getUser(userId);

                LoggerFactory.getLogger(RegisterController.class).info("date result: {}", dateTime);

                Appointment appointment = new Appointment(id, dateTime, status, type, user);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public List<Appointment> getAppointmentsForNurse(String username) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM appointments where type = ? ORDER BY date");
            statement.setString(1, "TREATMENT");
            resultSet = statement.executeQuery();
            List<Appointment> appointments = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                int id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String userId = resultSet.getString("user_id");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                User user = UserDaoImpl.getInstance().getUser(userId);

                LoggerFactory.getLogger(RegisterController.class).info("date result: {}", dateTime);

                Appointment appointment = new Appointment(id, dateTime, status, type, user);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public Appointment findByUsername(String username) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM appointments WHERE username = ? ");
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            User user = UserDaoImpl.getInstance().getUser(username);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                int id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String userId = resultSet.getString("user_id");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                return new Appointment(id, dateTime, status, type, user);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public Appointment findById(int appointmentId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM appointments WHERE id = ? ");
            statement.setInt(1, appointmentId);
            resultSet = statement.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                int id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String userId = resultSet.getString("user_id");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                User user = UserDaoImpl.getInstance().getUser(userId);

                return new Appointment(id, dateTime, status, type, user);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement, resultSet);
        }
    }

    @Override
    public boolean update(Appointment appointment) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE appointments SET date = ?, status = ?, type = ? WHERE id = ?");
            statement.setTimestamp(1, Timestamp.valueOf(appointment.getTime()));
            statement.setString(2, appointment.getStatusString().toString());
            statement.setString(3, appointment.getType().toString());
            statement.setInt(4, appointment.getId());

            int numRowsAffected = statement.executeUpdate();
            return numRowsAffected > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            close(statement, null);
        }
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("DELETE FROM appointments WHERE id = ?");
            statement.setInt(1, appointmentId);

            int numRowsAffected = statement.executeUpdate();
            return numRowsAffected > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            close(statement, null);
        }
    }

    @Override
    public boolean rescheduleAppointment(int appointmentId, LocalDateTime date) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE appointments SET date = ? WHERE id = ?");
            statement.setTimestamp(1, Timestamp.valueOf(date));
            statement.setInt(2, appointmentId);

            int numRowsAffected = statement.executeUpdate();
            return numRowsAffected > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            close(statement, null);
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
