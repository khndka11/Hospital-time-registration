package com.example.time_registration.controller;

import com.example.time_registration.model.DTO.AppointmentDto;
import com.example.time_registration.model.entities.Appointment;
import com.example.time_registration.model.entities.User;
import com.example.time_registration.model.enums.AppointmentStatusEnum;
import com.example.time_registration.model.enums.AppointmentType;
import com.example.time_registration.services.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**Энгийн хэрэглэгчийн контроллер*/
@Controller
public class PatientController {
    private final AppointmentService appointmentService; // Цагийн сервис
    private User currentPatient = null;

    @Autowired
    public PatientController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

/** Сэшнд хадгалагдсан хэрэглэгчийг авч буцаана.*/
    private User getCurrentPatient(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentPatient = (User) session.getAttribute("user");

        if (currentPatient != null) {
            LoggerFactory.getLogger(RegisterController.class).info("session result: {}", currentPatient.getName());
        } else {
            LoggerFactory.getLogger(RegisterController.class).info("session result: null");
        }

        return currentPatient;
    }

/** Цагуудын жагсаалтыг дуудаж шинэчилнэ.*/
    private void updateAppointments(Model model) {
        if (currentPatient != null) {
            List<Appointment> appointments = appointmentService.getAppointmentsByUsername(currentPatient.getUsername());

            List<Appointment> waitingAppointments = appointments.stream()
                    .filter(appointment -> appointment.getStatusString().equals(AppointmentStatusEnum.WAITING)).toList();

            List<Appointment> otherAppointments = appointments.stream()
                    .filter(appointment -> !appointment.getStatusString().equals(AppointmentStatusEnum.WAITING)).toList();

            model.addAttribute("waitingAppointments", waitingAppointments);
            model.addAttribute("otherAppointments", otherAppointments);
        }
    }

/** Хэрэглэгчийн гэр хуудсыг буцаана. Сэшнд хадгалагдаагүй хэрэглэгч байвал логик хуудасруу буцаана.*/
    @GetMapping("/patient_home")
    public String showPatientHome(HttpServletRequest request, Model model) {
        currentPatient = getCurrentPatient(request);
        if (currentPatient != null) {
            model.addAttribute("user", currentPatient);
            updateAppointments(model);

            return "patient_home";
        } else {
            return "redirect:/login";
        }
    }

/** Цаг авах логик */
    @PostMapping("/add")
    public String addTransaction(@ModelAttribute("appointment")AppointmentDto appointmentDto, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Алдаа гарлаа");
            return "patient_home";
        }

        /** Товлосон цагыг тохирох форматтад оруулах хэрэгтэй */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(appointmentDto.getDate(), formatter);
        LoggerFactory.getLogger(RegisterController.class).info("add result: {}", dateTime);

        /** Ямар төрлийн цаг авч байгаагаас хамаарч төрлийг нь тохируулна */
        Appointment appointment = new Appointment(dateTime, currentPatient, AppointmentType.EXAMINATION);
        if (appointmentDto.getType().equals("treatment")) {
            appointment.setType(AppointmentType.TREATMENT);
        }

        /** Хэрвээ сервис цаг авч чадахгүй false утга буцаавал алдааны мессеж явуулна. */
        if (!appointmentService.scheduleAppointment(appointment)) {
            model.addAttribute("errorMessage", "Цаг авахад алдаа гарлаа");
            return "patient_home";
        }

//        updateWaitingAppointments(model);
        updateAppointments(model);
        /** Амжилттай цаг авсан мессеж буцаана. */
        model.addAttribute("successMessage", "Амжилттай цаг авлаа");

        return "patient_home";
    }

    /** Хэрэглэгч цаг цуцлах логик. */
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelAppointment(@RequestParam("appointmentId") int appointmentId) {
        boolean success = appointmentService.cancelAppointment(appointmentId);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** Хэрэглэгч цагаа хойшлуулах логик */
    @PostMapping("/reschedule")
    public ResponseEntity<String> rescheduleAppointment(@RequestParam("appointmentId") int appointmentId, @RequestParam("newDateTime") String newDateTime) {

        LocalDateTime dateTime = LocalDateTime.parse(newDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        boolean success = appointmentService.rescheduleAppointment(appointmentId, dateTime);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** Хэрэглэгч гарах логик */
    @GetMapping("/patient/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }

}
