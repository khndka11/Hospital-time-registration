package com.example.time_registration.services;

import com.example.time_registration.controller.RegisterController;
import com.example.time_registration.model.DAO.AppointmentDaoImpl;
import com.example.time_registration.model.entities.Appointment;
import com.example.time_registration.model.enums.AppointmentStatusEnum;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/** Цагийн сервис загвар */
@Service
public class AppointmentService {

    private final AppointmentDaoImpl appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentDaoImpl appointmentDaoImpl) {
        this.appointmentRepository = appointmentDaoImpl;
    }

    /** Хэрэглэгчийн нэвтрэх нэр дээр бүртгэлтэй цагийн жагсаалтыг буцаана.
    Мөн хэрвээ ӨС-аас авсан цагуудын хугацаа нь өнгөрсөн бас үзүүлэгүй бол цуцлах төлөвт шилжүүлнэ. */
    public List<Appointment> getAppointmentsByUsername(String username) {
        List<Appointment> appointments = appointmentRepository.getAppointmentsByUsername(username);
        LocalDate currentDate = LocalDate.now();

        for (Appointment appointment : appointments) {
            if (appointment.getStatusString().equals(AppointmentStatusEnum.WAITING)
                    && appointment.getTime().toLocalDate().isBefore(currentDate)) {
                LoggerFactory.getLogger(RegisterController.class).info("changing to cancelled state");
                appointment.getStatus().canceled();
                appointmentRepository.update(appointment);
            }
        }

        return appointmentRepository.getAppointmentsByUsername(username);
    }

    /** Эмчид цагийн жагсаалтыг буцаана */
    public List<Appointment> getAppointmentsForDoctor(String username) {
        /** username ашиглахгүй байгааг анхаар!!! */
        List<Appointment> appointments = appointmentRepository.getAppointmentsForDoctor(username);
        LocalDate currentDate = LocalDate.now();

        for (Appointment appointment : appointments) {
            if (appointment.getStatusString().equals(AppointmentStatusEnum.WAITING)
                    && appointment.getTime().toLocalDate().isBefore(currentDate)) {
                LoggerFactory.getLogger(RegisterController.class).info("changing to cancelled state");
                appointment.getStatus().canceled();
                appointmentRepository.update(appointment);
            }
        }

        return appointmentRepository.getAppointmentsForDoctor(username);
    }

    /** Сувилагчид цагуудыг буцаана */
    public List<Appointment> getAppointmentsForNurse(String username) {
        /** username ашиглахгүй байгааг анхаар!!! */
        List<Appointment> appointments = appointmentRepository.getAppointmentsForNurse(username);
        LocalDate currentDate = LocalDate.now();

        for (Appointment appointment : appointments) {
            if (appointment.getStatusString().equals(AppointmentStatusEnum.WAITING)
                    && appointment.getTime().toLocalDate().isBefore(currentDate)) {
                LoggerFactory.getLogger(RegisterController.class).info("changing to cancelled state");
                appointment.getStatus().canceled();
                appointmentRepository.update(appointment);
            }
        }

        return appointmentRepository.getAppointmentsForNurse(username);
    }

    /** Цаг товлох */
    public boolean scheduleAppointment(Appointment appointment) {
        return appointmentRepository.addAppointment(appointment);
    }

    /** Цаг цуцлах */
    public boolean cancelAppointment(int appointmentId) {
        // Retrieve the appointment from the repository
        Appointment appointment = appointmentRepository.findById(appointmentId);

        if (appointment != null) {
            appointment.getStatus().canceled();
            return appointmentRepository.update(appointment);
        }

        return false;
    }

    /** Цаг үзсэн төлөвт шилжүүлэх */
    public boolean doneAppointment(int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId);

        if (appointment != null) {

            appointment.getStatus().done();
            return appointmentRepository.update(appointment);
        }

        return false;
    }

    /** Цаг устгах */
    public boolean deleteAppointment(int appointmentId) {
        return appointmentRepository.deleteAppointment(appointmentId);
    }

    /** Цаг хойшлуулах */
    public boolean rescheduleAppointment(int appointmentId, LocalDateTime date) {


        return appointmentRepository.rescheduleAppointment(appointmentId, date);
    }
}

