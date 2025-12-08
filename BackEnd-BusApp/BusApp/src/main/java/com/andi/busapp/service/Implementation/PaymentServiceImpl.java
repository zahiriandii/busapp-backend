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

        System.out.println("=== createPaymentIntent ===");
        System.out.println("tripId          = " + trip.getId());
        System.out.println("pricePerTicket  = " + pricePerTicket);
        System.out.println("passengersCount = " + passengers);
        System.out.println("amountInCents   = " + amountsInCents);
        System.out.println("paymentMethodId = " + request.paymentMethodId());


        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amountsInCents)
                        .setCurrency("eur")
                        .setPaymentMethod(request.paymentMethodId())
                        .setConfirm(true)
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .setAllowRedirects(
                                                PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER
                                        )
                                        .build()
                        )
                        .build();


        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return new PaymentIntentResponse(paymentIntent.getClientSecret(), paymentIntent.getId(),paymentIntent.getStatus());
        } catch (StripeException e) {
            System.err.println("StripeException while creating PaymentIntent:");
            System.err.println("  message = " + e.getMessage());
            if (e.getStripeError() != null) {
                System.err.println("  type    = " + e.getStripeError().getType());
                System.err.println("  code    = " + e.getStripeError().getCode());
                System.err.println("  stripeMessage = " + e.getStripeError().getMessage());
            }
            e.printStackTrace();
            throw new RuntimeException("Stripe error " + e.getMessage(),e);
        }
        catch (Exception e) {
            System.err.println("Non-Stripe exception in createPaymentIntent");
            e.printStackTrace();
            throw e;
        }

    }
}
