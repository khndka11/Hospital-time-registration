package com.example.time_registration.model.entities;

import com.example.time_registration.model.enums.UserType;

import java.util.ArrayList;
import java.util.List;

/** Сувилагч */
public class Nurse extends Person {
    private List<Appointment> appointments;

    public Nurse(String name, String username, String phoneNumber, String password, List<Appointment> appointments) {
        super(name, username, phoneNumber, password, UserType.NURSE);
        this.appointments = appointments;
    }

    public Nurse(String name, String username, String phoneNumber, String password) {
        super(name, username, phoneNumber, password, UserType.NURSE);
    }

    public Nurse(Person user) {
        super(user.getName(), user.getUsername(), user.getPhoneNumber(), user.getPassword(), UserType.NURSE);
        appointments = new ArrayList<>();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
