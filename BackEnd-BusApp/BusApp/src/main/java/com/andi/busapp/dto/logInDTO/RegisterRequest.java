package com.andi.busapp.dto.logInDTO;

import java.time.LocalDate;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        String gender,
        LocalDate birthDate,
        String country
) {}

