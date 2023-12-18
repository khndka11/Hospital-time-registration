package com.example.time_registration.model.entities;

import com.example.time_registration.model.enums.UserType;

import java.util.ArrayList;
import java.util.List;

/** Эмч */
public class Doctor extends Person {
    private List<Appointment> appointments;

    public Doctor(String name, String username, String phoneNumber, String password, List<Appointment> appointments) {
        super(name, username, phoneNumber, password, UserType.DOCTOR);
        this.appointments = appointments;
    }
    public Doctor(String name, String username, String phoneNumber, String password) {
        super(name, username, phoneNumber, password, UserType.DOCTOR);
        this.appointments = new ArrayList<>();
    }

    public Doctor(Person user) {
        super(user.getName(), user.getUsername(), user.getPhoneNumber(), user.getPassword(), UserType.DOCTOR);
        this.appointments = new ArrayList<>();
    }

    public Doctor(Doctor doctor) {
        super(doctor.getName(), doctor.getUsername(), doctor.getPhoneNumber(), doctor.getPassword(), UserType.DOCTOR);
        this.appointments = new ArrayList<>(doctor.getAppointments());
    }


    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
