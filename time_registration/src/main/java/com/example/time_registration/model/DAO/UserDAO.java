package com.example.time_registration.model.DAO;

import com.example.time_registration.model.entities.User;

public interface UserDAO {
    boolean addUser(User user); /** Хэрэглэгч нэмнэ */
    User findByUsername(String username); /**  Нэвтрэх нэрээр нь хайна */
    User findByUsernameAndPassword(String username, String password); /** Нэвтрэх нэр болон нууц үгээр нь хайна */

    User getUser(String username); /** Нэвтрэх нэрээр нь хайна */
}
