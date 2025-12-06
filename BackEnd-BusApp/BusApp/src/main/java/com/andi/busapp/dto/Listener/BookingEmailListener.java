package com.andi.busapp.dto.Listener;

import com.andi.busapp.dto.BookingCreationEvent;
import com.andi.busapp.entity.Booking;
import com.andi.busapp.service.Implementation.BookingEmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingEmailListener
{
    private final BookingEmailServiceImpl bookingEmailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBookingCreated(BookingCreationEvent event) {
        Booking booking = event.booking();

        try {
            bookingEmailService.sendBookingConfirmation(booking);
        } catch (MailException e) {
            log.error("Failed to send booking confirmation email for booking {}", booking.getId(), e);
        }
    }
}
