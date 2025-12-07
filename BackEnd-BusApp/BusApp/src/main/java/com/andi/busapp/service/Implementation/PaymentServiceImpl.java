package com.andi.busapp.service.Implementation;

import com.andi.busapp.dto.PaymentIntent.PaymentIntentRequestDTO;
import com.andi.busapp.dto.PaymentIntent.PaymentIntentResponse;
import com.andi.busapp.entity.Trip;
import com.andi.busapp.exceptions.TripNotFoundException;
import com.andi.busapp.repository.TripRepository;
import com.andi.busapp.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService
{
    private final TripRepository tripRepository;

    public PaymentServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequestDTO request) {

        Trip trip = this.tripRepository.findById(request.tripId())
                .orElseThrow(()-> new TripNotFoundException(request.tripId()));
        int passengers = request.passengers().size();

        long pricePerTicket = trip.getPrice();
        long amountsInCents = (pricePerTicket * 100) * passengers;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountsInCents)
                .setCurrency("eur")
                .build();


        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return new PaymentIntentResponse(paymentIntent.getClientSecret(), paymentIntent.getId());
        } catch (StripeException e) {
            throw new RuntimeException("Stripe error " + e.getMessage(),e);
        }

    }
}
