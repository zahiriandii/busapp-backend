package com.andi.busapp.dto;

import java.time.LocalDateTime;

public record TripDTO(
        Long tripId,
        String cityFrom,
        String cityTo,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        int seats,
        Long price
)
{}
