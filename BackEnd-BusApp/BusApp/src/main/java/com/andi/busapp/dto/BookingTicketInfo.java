package com.andi.busapp.dto;

import com.andi.busapp.dto.AdminBookingDTO.PassengerDTO;
import com.andi.busapp.entity.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public record BookingTicketInfo(
        Long bookingId,
        Long tripId,
        String firstName,
        String lastName,
        String contactEmail,
        BookingStatus status,
        LocalDateTime dateCreated,
        List<PassengerDTO> passengers
) {
}
