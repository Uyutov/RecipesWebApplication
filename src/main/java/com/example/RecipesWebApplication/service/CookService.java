package com.example.RecipesWebApplication.service;

import com.example.RecipesWebApplication.entity.Cook;
import com.example.RecipesWebApplication.repository.CookRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CookService implements UserDetailsService {

    private final CookRepository cookRepository;
    private final PasswordEncoder passwordEncoder;
    public CookService(CookRepository cookRepository, PasswordEncoder passwordEncoder) {
        this.cookRepository = cookRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        return cookRepository.findOneByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }

    public Cook getCookById(Long cook_id)
    {
        return cookRepository.findById(cook_id).get();
    }

    public Cook getCookByUsername(String username)
    {
        return cookRepository.findOneByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }

}
