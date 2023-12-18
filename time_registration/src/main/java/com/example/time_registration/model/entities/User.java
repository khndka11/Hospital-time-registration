package com.example.time_registration.model.entities;

import com.example.time_registration.model.enums.UserType;

/** Энгийн хэрэглэгч */
public class User extends Person {
    private Appointment appointment;

    public User(String name, String username, String phoneNumber, String password) {
        super(name, username, phoneNumber, password, UserType.PATIENT);
    }

    public User(String name, String username, String phoneNumber, String password, Appointment appointment) {
        super(name, username, phoneNumber, password, UserType.PATIENT);
        this.appointment = appointment;
    }

    public User(Person user) {
        super(user.getName(), user.getUsername(), user.getPhoneNumber(), user.getPassword(), UserType.PATIENT);
    }

    public User() {

    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

}
