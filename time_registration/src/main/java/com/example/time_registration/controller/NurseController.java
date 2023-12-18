package com.example.time_registration.controller;

import com.example.time_registration.model.entities.Appointment;
import com.example.time_registration.model.entities.Nurse;
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


/** Сувилагчийг хариуцсан контроллер */
@Controller
public class NurseController {
    private Nurse currentNurse = null;
    private final AppointmentService appointmentService;

    @Autowired
    public NurseController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    /** Сэшнд хадгалагдсан хэрэглэгчийг авна. Хэрвээ байхгүй бол нүлл буцаана. */
    private Nurse getCurrentNurse(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Nurse currentNurse = (Nurse) session.getAttribute("user");

        if (currentNurse != null) {
            LoggerFactory.getLogger(RegisterController.class).info("session result: {}", currentNurse.getName());
        } else {
            LoggerFactory.getLogger(RegisterController.class).info("session result: null");
        }

        return currentNurse;
    }


    /** Цагуудыг өгөгдлийн сангаас дуудаж шинэчилнэ. Хүлээгдэж буй цагууд болон бусад цагуудыг ялгана */
    private void updateAppointments(Model model) {
        if (currentNurse != null) {
            List<Appointment> appointments = appointmentService.getAppointmentsForNurse(currentNurse.getUsername());

            List<Appointment> waitingAppointments = appointments.stream()
                    .filter(appointment -> appointment.getStatusString().equals(AppointmentStatusEnum.WAITING)).toList();

            List<Appointment> otherAppointments = appointments.stream()
                    .filter(appointment -> !appointment.getStatusString().equals(AppointmentStatusEnum.WAITING)).toList();

            model.addAttribute("waitingAppointments", waitingAppointments);
            model.addAttribute("otherAppointments", otherAppointments);
        }
    }


/** Сувилагчийн гэр хуудсыг буцаах ба эхлээд сэшнд хадгалагдсан хэрэглэгчийг олж цагуудаа дуудаж шинэчилнэ. */
    @GetMapping("/nurse_home")
    public String showNurseHome(HttpServletRequest request, Model model) {
        currentNurse = getCurrentNurse(request);
        if (currentNurse != null) {
            model.addAttribute("user", currentNurse);
            updateAppointments(model);

            return "nurse_home";
        } else {
            return "redirect:/login";
        }
    }


    /** Сувилагч цагыг үзсэн гэж тэмдэглэх логик */
    @PostMapping("/nurse/done")
    public ResponseEntity<String> doneAppointment(@RequestParam("appointmentId") int appointmentId) {
        boolean success = appointmentService.doneAppointment(appointmentId);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /** Сувилагч цагийг устгах логик */
    @PostMapping("/nurse/delete")
    public ResponseEntity<String> deleteAppointment(@RequestParam("appointmentId") int appointmentId) {
        boolean success = appointmentService.deleteAppointment(appointmentId);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /** Сувилагч цагийг цуцлах логик */
    @PostMapping("/nurse/cancel")
    public ResponseEntity<String> cancelAppointment(@RequestParam("appointmentId") int appointmentId) {
        boolean success = appointmentService.cancelAppointment(appointmentId);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /** Гарах логик */
    @GetMapping("/nurse/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }
}
