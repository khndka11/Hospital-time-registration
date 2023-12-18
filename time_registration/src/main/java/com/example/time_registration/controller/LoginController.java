package com.example.time_registration.controller;

import com.example.time_registration.model.DTO.UserLoginDto;
import com.example.time_registration.model.entities.Doctor;
import com.example.time_registration.model.entities.Nurse;
import com.example.time_registration.model.entities.Person;
import com.example.time_registration.model.entities.User;
import com.example.time_registration.model.enums.UserType;
import com.example.time_registration.model.factories.UserFactory;
import com.example.time_registration.services.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


/** Нэвтрэх контроллер */
@Controller
public class LoginController {

    private final AuthenticationService authenticationService; // Нэвтрэх сервис

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    /** Пост үйлдлээр хэрэглэгч нэвтрэхэд шаардлагатай шалгалтыг явуулж нэвтрүүлнэ. Мөн сэшнд хэрэглэгчийн мэдээллийг хадгална. */
    @PostMapping("/login")
    public String verifyLogin(@ModelAttribute("user") UserLoginDto user, BindingResult bindingResult, HttpSession session, Model model) {

        if (bindingResult.hasErrors()) {
            return "login";
        }


    /** Хэрэглэгчийг сервис ашиглан нэвтрэх логикийг гүйцэтгэнэ */
        Person authResult = authenticationService.authenticate(user);
        LoggerFactory.getLogger(RegisterController.class).info("Login result: {}", authResult + " : " + session);


        /** Амжилттай нэвтэрвэл хэрэглэгчээс хамаараад тохирсон хуудсыг буцаах ба сэшнд хэрэглэгчийг хадгална. */
        if (authResult != null) {
            String userRole = authResult.getRole();
            switch (userRole) {
                case "DOCTOR" -> {
                    session.setAttribute("user", UserFactory.createUser(authResult, UserType.DOCTOR));
                    return "redirect:/doctor_home";
                }
                case "NURSE" -> {
                    session.setAttribute("user", UserFactory.createUser(authResult, UserType.NURSE));
                    return "redirect:/nurse_home";
                }
                case "PATIENT" -> {
                    session.setAttribute("user", UserFactory.createUser(authResult, UserType.PATIENT));
                    LoggerFactory.getLogger(RegisterController.class).info("Login user result: {}", session.getAttribute("user"));
                    return "redirect:/patient_home";
                }
            }
        }


        /** Энэд хүрвэл алдаа гарсан гэж үзнэ. */
        model.addAttribute("errorMessage", "Нэвтрэх нэр эсвэл нууц үг буруу");
        return "login";
    }
}
