package com.andi.busapp.dto;

import com.andi.busapp.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;

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
