package com.example.time_registration.services;

import com.example.time_registration.controller.RegisterController;
import com.example.time_registration.model.DAO.PersonDaoImpl;
import com.example.time_registration.model.DTO.UserLoginDto;
import com.example.time_registration.model.DTO.UserRegisterDto;
import com.example.time_registration.model.entities.Person;
import com.example.time_registration.model.enums.UserType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/** Бүртгэлийн сервис загвар */
@Repository
public class RegistrationService {
    private final PersonDaoImpl personDao;

    @Autowired
    public RegistrationService(PersonDaoImpl personDao) {
        this.personDao = personDao;
    }

    /** Бүртгэх метод */
    public boolean register(UserRegisterDto userDto) {
        /** Энэд бусад шалгах шалгууруудыг хийж болно. */

        Person newUser = new Person(userDto.getName(), userDto.getUsername(), userDto.getPhoneNo(), userDto.getPassword(), UserType.PATIENT);
        LoggerFactory.getLogger(RegisterController.class).info("user: " + userDto.getName() + userDto.getUsername() + userDto.getPhoneNo() + userDto.getPassword());
        return personDao.addUser(newUser);
    }
}
