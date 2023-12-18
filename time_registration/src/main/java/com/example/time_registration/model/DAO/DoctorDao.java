package com.example.time_registration.model.DAO;

import com.example.time_registration.model.entities.Doctor;

public interface DoctorDao {
    boolean addUser(Doctor user); /** Хэрэглэгч нэмнэ*/
    Doctor findByUsername(String username); /** Нэвтрэх нэрээр нь хайна*/
    Doctor findByUsernameAndPassword(String username, String password); /** Нэвтрэх нэр болон нууц үгээр хайна */

    Doctor getUser(String username); /** Нэвтрэх нэрээр нь хайна */
}
