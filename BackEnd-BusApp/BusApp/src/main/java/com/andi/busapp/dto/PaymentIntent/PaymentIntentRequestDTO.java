package com.andi.busapp.dto.PaymentIntent;

import com.andi.busapp.dto.RequestDTO.PassengerRequest;

import java.util.List;

public record PaymentIntentRequestDTO(
        Long tripId,
        List<PassengerRequest> passengers,
        String paymentMethodId
)
{}
