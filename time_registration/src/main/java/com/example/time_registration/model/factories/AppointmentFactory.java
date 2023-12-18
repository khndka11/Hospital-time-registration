package com.example.time_registration.model.factories;

import com.example.time_registration.model.entities.Appointment;
import com.example.time_registration.model.entities.ExaminationAppointment;
import com.example.time_registration.model.entities.TreatmentAppointment;
import com.example.time_registration.model.entities.User;
import com.example.time_registration.model.enums.AppointmentType;

import java.time.LocalDateTime;

/** Цагын фактори метод */
public class AppointmentFactory {
    public static Appointment createAppointment(User user, LocalDateTime date, AppointmentType type) {
        Appointment appointment = null;
        switch (type) {
            case EXAMINATION -> appointment = new ExaminationAppointment(date, user);
            case TREATMENT -> appointment = new TreatmentAppointment(date, user);
            default -> {
            }
        }
        return appointment;
    }
}
