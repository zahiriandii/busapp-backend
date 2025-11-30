package com.andi.busapp.dto.SeatStatus;

public record SeatStatusDTO(
        Long seatId,
        String seatNumber,
        boolean reserved
)
{}
