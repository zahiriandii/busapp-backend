package com.andi.busapp.dto.RequestDTO;

import com.andi.busapp.entity.enums.PassengerType;

public record PassengerRequest(
    String firtName,
    String lastName,
    PassengerType type,
    String seatNumber
)
{}
