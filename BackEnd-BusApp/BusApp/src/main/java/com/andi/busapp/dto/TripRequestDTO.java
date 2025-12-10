package com.andi.busapp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TripRequestDTO(
        Long cityFromId,
        Long cityToId,
        LocalDate departureDate,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        Long price,
        Long busId
)
{}
