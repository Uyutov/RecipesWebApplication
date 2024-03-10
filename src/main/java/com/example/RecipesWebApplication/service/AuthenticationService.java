package com.example.RecipesWebApplication.service;

import com.example.RecipesWebApplication.DTO.LoginResponseDTO;
import com.example.RecipesWebApplication.entity.Cook;
import com.example.RecipesWebApplication.entity.Role;
import com.example.RecipesWebApplication.repository.CookRepository;
import com.example.RecipesWebApplication.repository.RoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private final CookRepository cookRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AuthenticationService(CookRepository cookRepository, RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                 TokenService tokenService) {
        this.cookRepository = cookRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public Cook registerUser(String email, String nickname, String password){

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("COOK").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return cookRepository.save(new Cook(0L, email, nickname, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(String username, String password){

        try{

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(cookRepository.findOneByEmail(username).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

}
