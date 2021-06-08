package com.example.finalproject.service.impl;

import com.example.finalproject.exceptions.NotFoundException;
import com.example.finalproject.model.Event;
import com.example.finalproject.model.User;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImp implements UserService {

private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public User updateUser(User user) {
        User savedUser = getUserById(user.getId());
        savedUser.setUsername(Optional.ofNullable(user.getUsername()).orElse(savedUser.getUsername()));
        savedUser.setPassword(Optional.ofNullable(user.getPassword()).orElse(savedUser.getPassword()));

        return userRepository.save(savedUser);
    }


    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers()
    {
        return this.userRepository.findAll();
    }


    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void deleteUser(int userId) {
        User currentUser = getUserById(userId);
        userRepository.delete(currentUser);
    }

}
