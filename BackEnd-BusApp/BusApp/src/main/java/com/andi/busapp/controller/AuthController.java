package com.andi.busapp.controller;

import com.andi.busapp.dto.logInDTO.AuthResponse;
import com.andi.busapp.dto.logInDTO.LoginRequest;
import com.andi.busapp.dto.logInDTO.RegisterRequest;
import com.andi.busapp.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/logIn")
    public AuthResponse logIn (@RequestBody LoginRequest loginRequest)
    {
        return this.authenticationService.login(loginRequest);
    }

    @PostMapping("/register")
    public AuthResponse register (@RequestBody RegisterRequest registerRequest)
    {
        return this.authenticationService.register(registerRequest);
    }

}
