package com.example.finalproject.security;

import com.example.finalproject.exceptions.NotFoundException;
import com.example.finalproject.model.User;
import com.example.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt= userRepository.findUserByUsername(username);
        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("Username not found in the database"));
        return new SecurityUser(user);
    }
}
