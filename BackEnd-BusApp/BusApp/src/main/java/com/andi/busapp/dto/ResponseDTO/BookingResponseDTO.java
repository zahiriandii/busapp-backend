package com.andi.busapp.dto.ResponseDTO;

import com.andi.busapp.entity.enums.BookingStatus;

public record BookingResponseDTO(
        Long bookingId,
        Long tripId,
        String firstName,
        String lastName,
        String contactEmail,
        BookingStatus status
)
{}
