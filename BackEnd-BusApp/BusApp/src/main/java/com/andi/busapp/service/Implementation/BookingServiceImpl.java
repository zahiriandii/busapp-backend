package com.andi.busapp.service.Implementation;

import com.andi.busapp.dto.AdminBookingDTO.PassengerDTO;
import com.andi.busapp.dto.BookingCreationEvent;
import com.andi.busapp.dto.BookingTicketInfo;
import com.andi.busapp.dto.RequestDTO.CreateBookingRequest;
import com.andi.busapp.dto.RequestDTO.PassengerRequest;
import com.andi.busapp.dto.ResponseDTO.BookingResponseDTO;
import com.andi.busapp.entity.*;
import com.andi.busapp.entity.enums.BookingStatus;
import com.andi.busapp.entity.enums.PassengerType;
import com.andi.busapp.entity.enums.SeatStatus;
import com.andi.busapp.exceptions.SeatAlreadyReservedException;
import com.andi.busapp.exceptions.TripNotFoundException;
import com.andi.busapp.repository.BookingRepository;
import com.andi.busapp.repository.SeatRepository;
import com.andi.busapp.repository.SeatReservationRepository;
import com.andi.busapp.repository.TripRepository;
import com.andi.busapp.service.BookingService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService
{
    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;
    private final SeatReservationRepository seatReservationRepository;
    private final BookingEmailServiceImpl bookingEmailService;
    private final ApplicationEventPublisher eventPublisher;

    public BookingServiceImpl(BookingRepository bookingRepository, TripRepository tripRepository, SeatRepository seatRepository, SeatReservationRepository seatReservationRepository, BookingEmailServiceImpl bookingEmailService, ApplicationEventPublisher eventPublisher) {
        this.bookingRepository = bookingRepository;
        this.tripRepository = tripRepository;
        this.seatRepository = seatRepository;
        this.seatReservationRepository = seatReservationRepository;
        this.bookingEmailService = bookingEmailService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Booking save(Booking booking) {
        return this.bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findAll() {
        return this.bookingRepository.findAll();
    }

    @Override
    public BookingTicketInfo findById(Long id) {
        Booking booking = this.bookingRepository.findById(id).orElse(null);
        List<PassengerDTO> passengerDtos = booking.getPassengers() == null
                ? List.of()
                : booking.getPassengers()
                .stream()
                .map(this::toPassengerDto)
                .toList();

        return new BookingTicketInfo(
            booking.getId(),
            booking.getTrip().getId(),
            booking.getFirstName(),
            booking.getLastName(),
            booking.getContactEmail(),
            booking.getStatus(),
            booking.getDateCreated(),
            passengerDtos
        );

    }

    @Override
    public void deleteById(Long id) {
        this.bookingRepository.deleteById(id);
    }


    @Transactional
    public BookingResponseDTO createBooking(CreateBookingRequest request) {

        // 1. Trip must exist
        Trip trip = tripRepository
                .findById(request.tripId())
                .orElseThrow(() -> new TripNotFoundException(request.tripId()));

        // 2. Collect seat IDs from passengers
        List<Long> seatIds = request.passengers()
                .stream()
                .map(PassengerRequest::seatId)
                .toList();

        // 3. Load all requested seats
        List<Seat> seats = seatRepository.findAllById(seatIds);

        if (seats.size() != seatIds.size()) {
            throw new RuntimeException("One or more seats do not exist");
        }

        // 4. Ensure all seats belong to this trip's bus
        Long tripBusId = trip.getBus().getId();
        boolean seatFromDifferentBus = seats.stream()
                .anyMatch(seat -> !seat.getBus().getId().equals(tripBusId));

        if (seatFromDifferentBus) {
            throw new RuntimeException("One or more seats do not belong to this trip's bus");
        }

        // 5. Check that none of these seats are already reserved for this trip
        List<SeatReservation> existingReservations =
                seatReservationRepository.findByTripAndSeatIn(trip, seats);

        if (!existingReservations.isEmpty()) {
            throw new SeatAlreadyReservedException("One or more seats are already reserved");
        }

        try {
            PaymentIntent intent = PaymentIntent.retrieve(request.paymentIntentId());
            if (!"succeeded".equals(intent.getStatus())) {
                throw new RuntimeException("Payment not completed");
            }
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        // 6. Create Booking
        Booking booking = new Booking();
        booking.setTrip(trip);
        booking.setFirstName(request.firstName());
        booking.setLastName(request.lastName());
        booking.setContactEmail(request.contactEmail());
        booking.setPaymentIntentId(request.paymentIntentId());
        booking.setDateCreated(LocalDateTime.now());
        booking.setStatus(BookingStatus.RESERVED);

        List<Passenger> passengers = new ArrayList<>();

        // Map seatId -> Seat for easy lookup
        Map<Long, Seat> seatById = seats.stream()
                .collect(Collectors.toMap(Seat::getId, s -> s));

        // 7. Create passengers
        for (PassengerRequest pReq : request.passengers()) {

            Seat seat = seatById.get(pReq.seatId());

            Passenger passenger = new Passenger();
            passenger.setFirstName(pReq.firstName());
            passenger.setLastName(pReq.lastName());
            passenger.setPassengerType(pReq.type());
            passenger.setSeatNumber(seat.getSeatNumber());
            passenger.setBooking(booking);

            passengers.add(passenger);
        }

        booking.setPassengers(passengers);

        // 8. Save booking (cascades passengers)
        Booking savedBooking = bookingRepository.save(booking);

        // 9. Create seat reservations
        List<SeatReservation> reservations = new ArrayList<>();

        for (Passenger passenger : savedBooking.getPassengers()) {

            // Find seat again based on passenger.seatNumber
            Seat seat = seats.stream()
                    .filter(s -> s.getSeatNumber().equals(passenger.getSeatNumber()))
                    .findFirst()
                    .orElseThrow();

            SeatReservation reservation = new SeatReservation();
            reservation.setTrip(trip);
            reservation.setSeat(seat);
            reservation.setPassenger(passenger);
            reservation.setBooking(savedBooking);
            reservation.setStatus(SeatStatus.RESERVED);

            reservations.add(reservation);
        }

        seatReservationRepository.saveAll(reservations);

        eventPublisher.publishEvent(new BookingCreationEvent(savedBooking));
        // 10. Return response
        return new BookingResponseDTO(
                savedBooking.getId(),
                savedBooking.getTrip().getId(),
                savedBooking.getFirstName(),
                savedBooking.getLastName(),
                savedBooking.getContactEmail(),
                savedBooking.getStatus()
        );
    }
    private PassengerDTO toPassengerDto(Passenger p) {
        return new PassengerDTO(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getPassengerType(),
                p.getSeatNumber()
        );
    }
}
