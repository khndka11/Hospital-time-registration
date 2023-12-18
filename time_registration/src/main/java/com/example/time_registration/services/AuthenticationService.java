package com.example.time_registration.services;

import com.example.time_registration.controller.RegisterController;
import com.example.time_registration.model.DAO.PersonDao;
import com.example.time_registration.model.DAO.PersonDaoImpl;
import com.example.time_registration.model.DTO.UserLoginDto;
import com.example.time_registration.model.entities.Person;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Бүртгэлийн сервис загвар */
@Service
public class AuthenticationService {

    private final PersonDaoImpl personDao;

    @Autowired
    public AuthenticationService(PersonDaoImpl personDao) {
        this.personDao = personDao;
    }

    /** Хэрэглэгчийг ӨС-аас хайж олдвол тухайн хэрэглэгчийн обьектыг буцаана. Эсрэг тохиолдолд нүлл буцаана. */
    public Person authenticate(UserLoginDto user) {
        Person authenticatedUser = personDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        LoggerFactory.getLogger(RegisterController.class).info("user: " + user.getUsername() + user.getPassword());
        return authenticatedUser;
    }
}

