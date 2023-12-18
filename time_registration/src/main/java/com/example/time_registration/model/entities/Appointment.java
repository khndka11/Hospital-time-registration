package com.example.time_registration.model.entities;

import com.example.time_registration.model.enums.AppointmentStatusEnum;
import com.example.time_registration.model.enums.AppointmentType;
import com.example.time_registration.model.statuses.AppointmentStatus;
import com.example.time_registration.model.statuses.CancelledStatus;
import com.example.time_registration.model.statuses.DoneStatus;
import com.example.time_registration.model.statuses.WaitingStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/** Цаг bean */
public class Appointment implements Serializable {
    private int id;
    private LocalDateTime time;
    private User user;

    private AppointmentType type;
    private AppointmentStatus status;

    public Appointment(int id, LocalDateTime time, User user) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.status = new WaitingStatus(this);
    }

    public Appointment(LocalDateTime time, User user, AppointmentType type) {
        this.time = time;
        this.user = user;
        this.type = type;
        this.status = new WaitingStatus(this);
    }

    public Appointment(int id, LocalDateTime time, String type, User user) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.status = new WaitingStatus(this);
        switch (type) {
            case "TREATMENT" -> this.type = AppointmentType.TREATMENT;
            case "EXAMINATION" -> this.type = AppointmentType.EXAMINATION;
        }
    }

    public Appointment(int id, LocalDateTime time, String status, String type, User user) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.status = new WaitingStatus(this);

        switch (status) {
            case "WAITING" -> this.status = new WaitingStatus(this);
            case "DONE" -> this.status = new DoneStatus(this);
            case "CANCELLED" -> this.status = new CancelledStatus(this);
        }

        switch (type) {
            case "TREATMENT" -> this.type = AppointmentType.TREATMENT;
            case "EXAMINATION" -> this.type = AppointmentType.EXAMINATION;
        }
    }


    public Appointment(LocalDateTime time, User user) {
        this.time = time;
        this.user = user;
        this.status = new WaitingStatus(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public AppointmentStatusEnum getStatusString() {
        if (status instanceof WaitingStatus) {
            return AppointmentStatusEnum.WAITING;
        } else if (status instanceof DoneStatus) {
            return AppointmentStatusEnum.DONE;
        } else if (status instanceof CancelledStatus) {
            return AppointmentStatusEnum.CANCELLED;
        } else {
            return AppointmentStatusEnum.UNKNOWN;
        }
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
