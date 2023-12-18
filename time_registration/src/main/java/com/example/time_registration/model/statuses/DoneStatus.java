package com.example.time_registration.model.statuses;

import com.example.time_registration.model.entities.Appointment;

public class DoneStatus implements AppointmentStatus {
    private Appointment appointment;

    public DoneStatus(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void done() {
        System.out.println("Appointment is already done.");
    }

    @Override
    public void canceled() {
        appointment.setStatus(new CancelledStatus(appointment));
        System.out.println("Appointment has been canceled.");
    }

    @Override
    public void waiting() {
        appointment.setStatus(new WaitingStatus(appointment));
        System.out.println("Appointment has been rescheduled to waiting state.");
    }
}
