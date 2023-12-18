package com.example.time_registration.model.statuses;

import com.example.time_registration.model.entities.Appointment;

public class CancelledStatus implements AppointmentStatus {
    private Appointment appointment;

    public CancelledStatus(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void done() {
        appointment.setStatus(new DoneStatus(appointment));
        System.out.println("Appointment has been marked as done.");
    }

    @Override
    public void canceled() {
        System.out.println("Appointment is already canceled.");
    }

    @Override
    public void waiting() {
        appointment.setStatus(new WaitingStatus(appointment));
        System.out.println("Appointment has been rescheduled to waiting state.");
    }
}
