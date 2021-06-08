package com.example.finalproject.service;

import com.example.finalproject.model.Event;
import com.example.finalproject.model.User;

import java.util.List;

public interface UserService {

    User addNewUser(User user);
    User updateUser(User user);
    User getUserById(int userId);
    User getUserByUsername(String username);
    void deleteUser(int userId);
    List<User> getAllUsers();

}
