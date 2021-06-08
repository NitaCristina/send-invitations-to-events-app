package com.example.finalproject.service.impl;

import com.example.finalproject.model.Authority;
import com.example.finalproject.repository.AuthorityRepository;
import com.example.finalproject.service.AuthorityService;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority addNewAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }
}
