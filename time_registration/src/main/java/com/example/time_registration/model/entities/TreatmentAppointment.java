package com.example.time_registration.model.entities;

import com.example.time_registration.model.enums.TreatmentType;

import java.time.LocalDateTime;

/** Эмчилгээний цаг */
public class TreatmentAppointment extends Appointment {

    private TreatmentType treatmentType;

    public TreatmentAppointment(int id, LocalDateTime time, User user) {
        super(id, time, user);
    }

    public TreatmentAppointment(LocalDateTime time, User user) {
        super(time, user);
    }

    public TreatmentAppointment(int id, LocalDateTime time, User user, TreatmentType treatmentType) {
        super(id, time, user);
        this.treatmentType = treatmentType;
    }

    public TreatmentAppointment(LocalDateTime time, User user, TreatmentType treatmentType) {
        super(time, user);
        this.treatmentType = treatmentType;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(TreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }
}
