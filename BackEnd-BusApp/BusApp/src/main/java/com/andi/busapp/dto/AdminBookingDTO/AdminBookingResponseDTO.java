package com.andi.busapp.dto.AdminBookingDTO;

import com.andi.busapp.entity.enums.BookingStatus;
import com.andi.busapp.entity.enums.PassengerType;

import java.util.List;

public record AdminBookingResponseDTO(
        Long bookingId,
        Long tripId,
        String firstName,
        String lastName,
        String contactEmail,
        BookingStatus status,
        List<PassengerDTO> passengers
)
{}
