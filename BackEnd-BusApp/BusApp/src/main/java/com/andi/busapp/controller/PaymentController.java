package com.andi.busapp.controller;

import com.andi.busapp.dto.PaymentIntent.PaymentIntentRequestDTO;
import com.andi.busapp.dto.PaymentIntent.PaymentIntentResponse;
import com.andi.busapp.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController
{
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-intent")
    public ResponseEntity<PaymentIntentResponse> createIntent(
            @RequestBody PaymentIntentRequestDTO request) {

        return ResponseEntity.ok(paymentService.createPaymentIntent(request));
    }
}
