package com.example.time_registration.model.statuses;

import com.example.time_registration.controller.RegisterController;
import com.example.time_registration.model.entities.Appointment;
import org.slf4j.LoggerFactory;

public class WaitingStatus implements AppointmentStatus {
    private Appointment appointment;

    public WaitingStatus(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void done() {
        appointment.setStatus(new DoneStatus(appointment));
        System.out.println("Appointment has been marked as done.");
        LoggerFactory.getLogger(RegisterController.class).info("Appointment has been marked as done.");
    }

    @Override
    public void canceled() {
        appointment.setStatus(new CancelledStatus(appointment));
        System.out.println("Appointment has been canceled.");
        LoggerFactory.getLogger(RegisterController.class).info("Appointment has been canceled.");
    }

    @Override
    public void waiting() {
        System.out.println("Appointment is already in the waiting state.");
        LoggerFactory.getLogger(RegisterController.class).info("Appointment is already in the waiting state.");
    }
}