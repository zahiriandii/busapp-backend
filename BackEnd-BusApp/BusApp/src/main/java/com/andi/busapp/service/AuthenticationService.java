package com.andi.busapp.service;

import com.andi.busapp.dto.logInDTO.AuthResponse;
import com.andi.busapp.dto.logInDTO.LoginRequest;
import com.andi.busapp.dto.logInDTO.RegisterRequest;
import com.andi.busapp.entity.User;

import java.time.LocalDate;

public interface AuthenticationService
{
    public AuthResponse login(LoginRequest loginRequest);
    public AuthResponse register(RegisterRequest registerRequest);
}
