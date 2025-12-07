package com.andi.busapp.service;

import com.andi.busapp.dto.PaymentIntent.PaymentIntentRequestDTO;
import com.andi.busapp.dto.PaymentIntent.PaymentIntentResponse;

public interface PaymentService
{
    PaymentIntentResponse createPaymentIntent(PaymentIntentRequestDTO request);
}
