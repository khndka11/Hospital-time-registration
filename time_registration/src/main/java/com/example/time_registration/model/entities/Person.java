package com.example.time_registration.model.entities;

import com.example.time_registration.model.enums.UserType;

import java.io.Serializable;

/** Хүн bean */
public class Person implements Serializable {
    private String name;
    private String username;
    private String phoneNumber;
    private String password;

    private UserType role;

    public Person() {

    }

    public Person(String name, String username, String phoneNumber, String password) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Person(String name, String username, String phoneNumber, String password, UserType role) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    public Person(String name, String username, String phoneNumber, String password, String role) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        switch (role) {
            case "DOCTOR" -> this.role = UserType.DOCTOR;
            case "NURSE" -> this.role = UserType.NURSE;
            case "PATIENT" -> this.role = UserType.PATIENT;
        }
    }

    public String getRole() {
        String returnRole = "PATIENT";
        switch (role) {
            case DOCTOR -> returnRole = "DOCTOR";
            case NURSE -> returnRole = "NURSE";
            case PATIENT -> returnRole = "PATIENT";
        }

        return returnRole;
    }

    public void setRole(UserType type) {
        this.role = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
