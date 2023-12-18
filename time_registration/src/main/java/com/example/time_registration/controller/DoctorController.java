package com.example.time_registration.controller;

import com.example.time_registration.model.entities.Appointment;
import com.example.time_registration.model.entities.Doctor;
import com.example.time_registration.model.enums.AppointmentStatusEnum;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/** Эмчийг хариуцсан контроллер */
@Controller
public class DoctorController {
    private Doctor currentDoctor = null;
    private final AppointmentService appointmentService; // Цаг авах сервис

    @Autowired
    public DoctorController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    /** Сэшнд хадгалагдсан хэрэглэгчийг авна. Хэрвээ байхгүй бол нүлл буцаана.*/
    private Doctor getCurrentDoctor(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Doctor currentDoctor = (Doctor) session.getAttribute("user");

        if (currentDoctor != null) {
            LoggerFactory.getLogger(RegisterController.class).info("session result: {}", currentDoctor.getName());
        } else {
            LoggerFactory.getLogger(RegisterController.class).info("session result: null");
        }

        return currentDoctor;
    }


/** Цагуудыг өгөгдлийн сангаас дуудаж шинэчилнэ. Хүлээгдэж буй цагууд болон бусад цагуудыг ялгана */
    private void updateAppointments(Model model) {
        if (currentDoctor != null) {
            List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(currentDoctor.getUsername());

            List<Appointment> waitingAppointments = appointments.stream()
                    .filter(appointment -> appointment.getStatusString().equals(AppointmentStatusEnum.WAITING)).toList();

            List<Appointment> otherAppointments = appointments.stream()
                    .filter(appointment -> !appointment.getStatusString().equals(AppointmentStatusEnum.WAITING)).toList();

            model.addAttribute("waitingAppointments", waitingAppointments);
            model.addAttribute("otherAppointments", otherAppointments);
        }
    }


    /** Эмчийн гэр хуудсыг буцаах ба эхлээд сэшнд хадгалагдсан хэрэглэгчийг олж цагуудаа дуудаж шинэчилнэ.*/
    @GetMapping("/doctor_home")
    public String showDoctorHome(HttpServletRequest request, Model model) {
        currentDoctor = getCurrentDoctor(request);
        if (currentDoctor != null) {
            model.addAttribute("user", currentDoctor);
            updateAppointments(model);

            return "doctor_home";
        } else {
            return "redirect:/login";
        }
    }


    /** Эмч цагыг үзсэн гэж тэмдэглэх логик */
    @PostMapping("/doctor/done")
    public ResponseEntity<String> doneAppointment(@RequestParam("appointmentId") int appointmentId) {
        boolean success = appointmentService.doneAppointment(appointmentId);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /** Эмч цагыг хойшлуулах логик */
    @PostMapping("/doctor/cancel")
    public ResponseEntity<String> cancelAppointment(@RequestParam("appointmentId") int appointmentId) {
        boolean success = appointmentService.cancelAppointment(appointmentId);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /** Эмч цагийг устгах логик */
    @PostMapping("/doctor/delete")
    public ResponseEntity<String> deleteAppointment(@RequestParam("appointmentId") int appointmentId) {
        boolean success = appointmentService.deleteAppointment(appointmentId);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /** Хэрэглэгч гарах логик */
    @GetMapping("/home/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }
}
