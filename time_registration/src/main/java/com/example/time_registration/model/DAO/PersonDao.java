package com.example.time_registration.model.DAO;

import com.example.time_registration.model.entities.Person;

public interface PersonDao {
    boolean addUser(Person user); /** Хэрэглэгч нэмнэ */
    Person findByUsername(String username); /** Нэвтрэх нэрээр нь хайна */
    Person findByUsernameAndPassword(String username, String password); /** Нэвтрэх нэр болон нууц үгээр нь хайна */

    Person getUser(String username); /** Нэвтрэх нэрээр нь хайна */
}
