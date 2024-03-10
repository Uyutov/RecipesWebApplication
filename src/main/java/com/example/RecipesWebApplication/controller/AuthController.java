package com.example.RecipesWebApplication.controller;

import com.example.RecipesWebApplication.DTO.LoginResponseDTO;
import com.example.RecipesWebApplication.DTO.RegistrationDTO;
import com.example.RecipesWebApplication.entity.Cook;
import com.example.RecipesWebApplication.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public Cook registrationUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getEmail(), body.getNickname(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getEmail(), body.getPassword());
    }
}
