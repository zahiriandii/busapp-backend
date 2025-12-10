package com.andi.busapp.dto.AdminBookingDTO;

import com.andi.busapp.entity.enums.PassengerType;

public record PassengerDTO(
        Long id,
        String firstName,
        String lastName,
        PassengerType passengerType,
        String seatNumber
)
{}
