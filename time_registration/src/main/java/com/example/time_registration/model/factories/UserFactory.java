package com.example.time_registration.model.factories;

import com.example.time_registration.model.entities.Doctor;
import com.example.time_registration.model.entities.Nurse;
import com.example.time_registration.model.entities.Person;
import com.example.time_registration.model.entities.User;
import com.example.time_registration.model.enums.UserType;

/** Хэрэглэгчийн фактори метод */
public class UserFactory {
    public static Person createUser(Person person, UserType type) {
        switch (type) {
            case DOCTOR:
                return new Doctor(person.getName(), person.getUsername(), person.getPhoneNumber(), person.getPassword());
            case NURSE:
                return new Nurse(person.getName(), person.getUsername(), person.getPhoneNumber(), person.getPassword());
            case PATIENT:
                return new User(person.getName(), person.getUsername(), person.getPhoneNumber(), person.getPassword());
            default:
                throw new IllegalArgumentException("Invalid user type: " + type);
        }
    }
}

