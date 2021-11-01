package com.example.finalproject.service;

import com.example.finalproject.model.User;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.service.impl.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldAddUserTest(){

        User user = new User(1, "Cristina", "123456");
        userServiceImp.addNewUser(user);
        verify(userRepository,times(1)).save(user);

    }

    @Test
    void shouldUpdateUser(){
        when (userRepository.findById(1)).thenReturn(Optional.of(new User(1, "Cristina", "123456")));
        User user = userServiceImp.getUserById(1);
        String newUsername = "Cristinaaa";

        user.setUsername(newUsername);
        this.userServiceImp.addNewUser(user);

        //test
        user = this.userServiceImp.getUserById(1);
        assertThat(user.getUsername()).isEqualTo(newUsername);
    }

    @Test
    void shouldFindUserById(){

        when (userRepository.findById(1)).thenReturn(Optional.of(new User(1, "Cristina", "123456")));
        User user = userServiceImp.getUserById(1);
        assertEquals("Cristina", user.getUsername());
        assertEquals("123456", user.getPassword());
    }

    @Test
    void shouldGetListOfUsers(){

        List<User> userList = new ArrayList<User>();
        User user1 = new User(1, "Cristina", "123456");
        User user2 = new User(2, "Andrei", "123456");
        User user3 = new User(3, "Simona", "123456");

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        when(userRepository.findAll()).thenReturn(userList);

        //test
        List<User> eList = userServiceImp.getAllUsers();

        assertEquals(3, eList.size());
        verify(userRepository, times(1)).findAll();

    }

    @Test
    @Transactional
    public void shouldDeleteUser(){
        User user = new User(1, "Cristina", "123456");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userServiceImp.deleteUser(user.getId());
        verify(userRepository).delete(user);
    }

}
