package com.example.time_registration.model.entities;

import com.example.time_registration.model.enums.ExaminationType;

import java.time.LocalDateTime;

/** Үзлэгийн цаг */
public class ExaminationAppointment extends Appointment {

    private ExaminationType examinationType;

    public ExaminationAppointment(int id, LocalDateTime time, User user) {
        super(id, time, user);
    }

    public ExaminationAppointment(LocalDateTime time, User user) {
        super(time, user);
    }

    public ExaminationAppointment(int id, LocalDateTime time, User user, ExaminationType examinationType) {
        super(id, time, user);
        this.examinationType = examinationType;
    }

    public ExaminationAppointment(LocalDateTime time, User user, ExaminationType examinationType) {
        super(time, user);
        this.examinationType = examinationType;
    }

    public ExaminationType getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(ExaminationType examinationType) {
        this.examinationType = examinationType;
    }
}
