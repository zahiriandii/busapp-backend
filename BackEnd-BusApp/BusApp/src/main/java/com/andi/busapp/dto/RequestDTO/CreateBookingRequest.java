package com.andi.busapp.dto.RequestDTO;

import java.util.List;

public record CreateBookingRequest(
    Long tripId,
    String firstName,
    String lastName,
    String contactEmail,
    List<PassengerRequest> passengers
)
{}
