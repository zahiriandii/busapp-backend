package com.andi.busapp.dto.RequestDTO;

import com.andi.busapp.entity.enums.PassengerType;

public record PassengerRequest(
    String firstName,
    String lastName,
    PassengerType type,
    Long seatId
)
{}
