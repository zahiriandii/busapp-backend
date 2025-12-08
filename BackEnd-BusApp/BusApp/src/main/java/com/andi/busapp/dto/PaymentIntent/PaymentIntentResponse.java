package com.andi.busapp.dto.PaymentIntent;

public record PaymentIntentResponse(
        String clientSecret,
        String paymentIntentId,
        String status
)
{}
