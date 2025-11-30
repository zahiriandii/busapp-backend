package com.andi.busapp.service.Implementation;

import com.andi.busapp.dto.SeatStatus.SeatStatusDTO;
import com.andi.busapp.entity.*;
import com.andi.busapp.entity.enums.SeatStatus;
import com.andi.busapp.exceptions.SeatAlreadyReservedException;
import com.andi.busapp.exceptions.TripNotFoundException;
import com.andi.busapp.repository.*;
import com.andi.busapp.service.SeatReservationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatReservationImpl implements SeatReservationService
{
    private final SeatReservationRepository seatReservationRepository;
    private final SeatRepository seatRepository;
    private final TripRepository tripRepository;
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    public SeatReservationImpl(SeatReservationRepository seatReservationRepository, SeatRepository seatRepository, TripRepository tripRepository, PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.seatReservationRepository = seatReservationRepository;
        this.seatRepository = seatRepository;
        this.tripRepository = tripRepository;
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public SeatReservation reserveSeat(Long tripId, Long seatId, Long bookingId, Long passengerId) {

        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        Seat seat = seatRepository.findById(seatId).orElseThrow(()-> new RuntimeException("Seat Not Found"));
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(()-> new RuntimeException("Passenger Not Found"));
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("Booking Not Found"));

        if (seatReservationRepository.existsByTripAndSeat(trip,seat))
        {
            throw new SeatAlreadyReservedException("Seat already reserved");
        }

        SeatReservation reservation = new SeatReservation();
        reservation.setTrip(trip);
        reservation.setSeat(seat);
        reservation.setPassenger(passenger);
        reservation.setBooking(booking);
        reservation.setStatus(SeatStatus.RESERVED);

        return seatReservationRepository.save(reservation);
    }

    @Override
    public List<SeatStatusDTO> getSeatStatusForTrip(Long tripId) {
        return seatReservationRepository.getSeatStatusByTrip(tripId);
    }
}
