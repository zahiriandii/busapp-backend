package com.andi.busapp.service.Implementation;


import com.andi.busapp.dto.RequestDTO.CreateBookingRequest;
import com.andi.busapp.dto.RequestDTO.PassengerRequest;
import com.andi.busapp.entity.*;
import com.andi.busapp.entity.enums.PassengerType;
import com.andi.busapp.exceptions.SeatAlreadyReservedException;
import com.andi.busapp.exceptions.TripNotFoundException;
import com.andi.busapp.repository.BookingRepository;
import com.andi.busapp.repository.SeatRepository;
import com.andi.busapp.repository.SeatReservationRepository;
import com.andi.busapp.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock BookingRepository bookingRepository;
    @Mock TripRepository tripRepository;
    @Mock SeatRepository seatRepository;
    @Mock SeatReservationRepository seatReservationRepository;
    @Mock BookingEmailServiceImpl bookingEmailService;
    @Mock ApplicationEventPublisher eventPublisher;

    @InjectMocks BookingServiceImpl bookingService;

    private Trip createTripWithBusAndSeats() {
        Bus bus = new Bus();
        bus.setId(10L);

        Seat seat1 = new Seat();
        seat1.setId(1L);
        seat1.setSeatNumber("1A");
        seat1.setBus(bus);

        Seat seat2 = new Seat();
        seat2.setId(2L);
        seat2.setSeatNumber("1B");
        seat2.setBus(bus);

        bus.setSeats(List.of(seat1, seat2));

        Trip trip = new Trip();
        trip.setId(5L);
        trip.setBus(bus);

        return trip;
    }

    @Test
    void createBooking_tripNotFound_throwsTripNotFoundException() {
        // given
        CreateBookingRequest request = new CreateBookingRequest(
                999L,
                "John",
                "Doe",
                "john@example.com",
                "pi_123",
                List.of()
        );

        when(tripRepository.findById(999L)).thenReturn(Optional.empty());

        // when + then
        assertThrows(TripNotFoundException.class,
                () -> bookingService.createBooking(request));
    }

    @Test
    void createBooking_seatsDoNotExist_throwsRuntimeException() {
        // given
        Trip trip = createTripWithBusAndSeats();

        PassengerRequest p1 = new PassengerRequest("A", "A", PassengerType.ADULT, 1L);
        PassengerRequest p2 = new PassengerRequest("B", "B", PassengerType.ADULT, 2L);

        CreateBookingRequest request = new CreateBookingRequest(
                trip.getId(),
                "John",
                "Doe",
                "john@example.com",
                "pi_123",
                List.of(p1, p2)
        );

        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));

        // simulate only 1 seat returned instead of 2
        when(seatRepository.findAllById(List.of(1L, 2L)))
                .thenReturn(List.of(trip.getBus().getSeats().get(0)));

        // when + then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookingService.createBooking(request));
        assertEquals("One or more seats do not exist", ex.getMessage());
    }

    @Test
    void createBooking_seatFromDifferentBus_throwsRuntimeException() {
        // given
        Trip trip = createTripWithBusAndSeats();
        List<Seat> seatsOfTripBus = trip.getBus().getSeats();

        // create a seat on another bus
        Bus otherBus = new Bus();
        otherBus.setId(99L);
        Seat foreignSeat = new Seat();
        foreignSeat.setId(3L);
        foreignSeat.setSeatNumber("9Z");
        foreignSeat.setBus(otherBus);

        PassengerRequest p1 = new PassengerRequest("A", "A", PassengerType.ADULT, seatsOfTripBus.get(0).getId());
        PassengerRequest p2 = new PassengerRequest("B", "B", PassengerType.ADULT, foreignSeat.getId());

        CreateBookingRequest request = new CreateBookingRequest(
                trip.getId(),
                "John",
                "Doe",
                "john@example.com",
                "pi_123",
                List.of(p1, p2)
        );

        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));

        // repository returns a list including the foreign seat
        when(seatRepository.findAllById(List.of(
                seatsOfTripBus.get(0).getId(),
                foreignSeat.getId()
        ))).thenReturn(List.of(seatsOfTripBus.get(0), foreignSeat));

        // when + then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookingService.createBooking(request));
        assertEquals("One or more seats do not belong to this trip's bus", ex.getMessage());
    }

    @Test
    void createBooking_seatsAlreadyReserved_throwsSeatAlreadyReservedException() {
        // given
        Trip trip = createTripWithBusAndSeats();
        List<Seat> seats = trip.getBus().getSeats();

        PassengerRequest p1 = new PassengerRequest("A", "A", PassengerType.ADULT, seats.get(0).getId());
        PassengerRequest p2 = new PassengerRequest("B", "B", PassengerType.ADULT, seats.get(1).getId());

        CreateBookingRequest request = new CreateBookingRequest(
                trip.getId(),
                "John",
                "Doe",
                "john@example.com",
                "pi_123",
                List.of(p1, p2)
        );

        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));
        when(seatRepository.findAllById(List.of(seats.get(0).getId(), seats.get(1).getId())))
                .thenReturn(seats);

        // simulate that reservations already exist
        when(seatReservationRepository.findByTripAndSeatIn(trip, seats))
                .thenReturn(List.of(new SeatReservation()));

        // when + then
        assertThrows(SeatAlreadyReservedException.class,
                () -> bookingService.createBooking(request));

        // Stripe is never reached here, so no static mocking needed
    }
}
