package com.andi.busapp.service.Implementation;

import com.andi.busapp.entity.Booking;
import com.andi.busapp.entity.Passenger;
import com.andi.busapp.entity.SeatReservation;
import com.andi.busapp.repository.SeatReservationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingEmailServiceImpl
{
    private final JavaMailSender mailSender;
    private final SeatReservationRepository seatReservationRepository;

    @Value("${app.mail.from:no-reply@bus-ticket.com}")
    private String from;
    public BookingEmailServiceImpl(JavaMailSender mailSender, SeatReservationRepository seatReservationRepository) {
        this.mailSender = mailSender;
        this.seatReservationRepository = seatReservationRepository;
    }

    public void sendBookingConfirmation (Booking booking)
    {
        if (booking.getContactEmail() == null || booking.getContactEmail().isBlank())
        {
            return;
        }
        String subject = "Your bus ticket reservation #" + booking.getId();

        String passengerLines = booking.getPassengers().stream()
                .map(p -> formatPassengerLine(p))
                .collect(Collectors.joining("\n"));

        List<SeatReservation> seatReservations = this.seatReservationRepository.findByBooking(booking);

        String seatLines = seatReservations.stream()
                .map(this::formatSeatLine)
                .collect(Collectors.joining("\n"));


        String body = """
        Hello %s,

        Thank you for your reservation with TestBus.

        Trip:
        From: %s
        To: %s
        Departure: %s
        Arrival: %s

        Passengers:
        %s

        Seats:
        %s

        Total price: %d €

        Booking ID: %d

        Have a nice trip!
        """.formatted(
                booking.getFirstName(),
                booking.getTrip().getCityFrom().getName(),
                booking.getTrip().getCityTo().getName(),
                booking.getTrip().getDepartureTime(),
                booking.getTrip().getArrivalTime(),
                passengerLines,
                seatLines,
                booking.getTrip().getPrice(),  // Long -> %d
                booking.getId()
        );
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(booking.getContactEmail());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

    }
    private String formatPassengerLine(Passenger p) {
        return "- " + p.getFirstName() + " " + p.getLastName() + " (" + p.getPassengerType() + ")";
    }

    private String formatSeatLine(SeatReservation r) {
        return "- Seat " + r.getSeat().getSeatNumber();
    }
}
