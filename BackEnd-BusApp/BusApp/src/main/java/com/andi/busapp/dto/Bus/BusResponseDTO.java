package com.andi.busapp.dto.Bus;

public record BusResponseDTO(
        Long id,
        String busName,
        String plateNumber,
        int seatCount
)
{}
