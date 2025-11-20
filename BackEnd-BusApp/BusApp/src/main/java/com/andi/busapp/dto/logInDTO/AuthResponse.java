package com.andi.busapp.dto.logInDTO;

public record AuthResponse(
        Long userId,
        String email,
        String firstName,
        String lastName
        // later: token, roles, etc.
) {}
