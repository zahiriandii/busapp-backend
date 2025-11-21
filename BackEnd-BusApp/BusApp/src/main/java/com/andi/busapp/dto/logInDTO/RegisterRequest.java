package com.andi.busapp.dto.logInDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Phone number is required")
        String phoneNumber,
        @NotBlank(message = "Gender is required")
        String gender,
        @NotNull(message = "Birthdate is required")
        LocalDate birthDate,
        @NotBlank(message = "Country is required")
        String country
) {}

