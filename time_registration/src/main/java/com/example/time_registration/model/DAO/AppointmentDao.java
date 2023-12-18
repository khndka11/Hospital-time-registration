package com.example.time_registration.model.DAO;

import com.example.time_registration.model.entities.Appointment;

import java.time.LocalDateTime;
import java.util.List;


/** Цаг товлох ДАО*/
public interface AppointmentDao {
    boolean addAppointment(Appointment appointment); // Цаг нэмэх

    void cancelAppointment(Appointment appointment); // Цаг хойшлуулах

    boolean deleteAppointment(int appointmentId); // Цаг устгах

    List<Appointment> getAppointments(); // Нэг цаг авах

    List<Appointment> getAppointmentsByUsername(String username); // Нэвтрэх нэр дээр бүртгэгдсэн цагуудыг буцаана
    List<Appointment> getAppointmentsForDoctor(String username); // Эмчид цагуудыг буцаана
    List<Appointment> getAppointmentsForNurse(String username); // Сувилагчид цагуудыг буцаана

    Appointment findByUsername(String username);

    Appointment findById(int appointmentId); // Нэвтрэх нэрээр нь нэг цаг буцаана

    boolean rescheduleAppointment(int appointmentId, LocalDateTime date); // Дахин цаг товлоно

    boolean update(Appointment appointment); // Сонгосон цагыг шинэ өгөгдлөөр шинэчилнэ
}
