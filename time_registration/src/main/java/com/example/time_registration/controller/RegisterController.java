package com.example.time_registration.controller;

import com.example.time_registration.model.DTO.UserRegisterDto;
import com.example.time_registration.services.RegistrationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


/** Хэрэглэгч бүртгүүлэх контроллер*/
@Controller
public class RegisterController {
    private final RegistrationService registrationService; // Бүртгүүлэх сервис

    @Autowired
    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }


    /** Пост байдлаар хэрэглэгчийн бүртгэл орж ирж амжилтай бүртгэвэл (өгөгдлийн санд хадгална) логин хуудасруу буцна.*/
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegisterDto user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            LoggerFactory.getLogger(RegisterController.class).info("Registration result: jfdkafjakl");
            return "register";
        }


    /** Бүртгэх логик */
        boolean registrationResult = registrationService.register(user);
        LoggerFactory.getLogger(RegisterController.class).info("Registration result: {}", registrationResult);

        if (registrationResult) {
            return "redirect:/login";
        } else {

    /** Алдаа гарвал */
            model.addAttribute("errorMessage", "Ийм нэвтрэх нэртэй хэрэглэгч байна");
            return "register";
        }
    }
}
