package com.example.time_registration.model.statuses;

/** Цагын статус буюу стейт үлгэр загвар */
public interface AppointmentStatus {
    void done();
    void canceled();
    void waiting();
}
