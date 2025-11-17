package com.andi.busapp.service.Implementation;

import com.andi.busapp.dto.RequestDTO.CreateBookingRequest;
import com.andi.busapp.dto.ResponseDTO.BookingResponseDTO;
import com.andi.busapp.entity.Booking;
import com.andi.busapp.entity.Passenger;
import com.andi.busapp.entity.Trip;
import com.andi.busapp.entity.enums.BookingStatus;
import com.andi.busapp.entity.enums.PassengerType;
import com.andi.busapp.exceptions.TripNotFoundException;
import com.andi.busapp.repository.BookingRepository;
import com.andi.busapp.repository.TripRepository;
import com.andi.busapp.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService
{
    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, TripRepository tripRepository) {
        this.bookingRepository = bookingRepository;
        this.tripRepository = tripRepository;
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
    public Booking findById(Long id) {
        return this.bookingRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        this.bookingRepository.deleteById(id);
    }


    @Transactional
    public BookingResponseDTO createBooking(CreateBookingRequest request) {

        Trip trip = tripRepository
                .findById(request.tripId())
                .orElseThrow(()-> new TripNotFoundException(request.tripId()));

        Booking booking = new Booking();
        booking.setTrip(trip);
        booking.setFirstName(request.firstName());
        booking.setLastName(request.lastName());
        booking.setContactEmail(request.contactEmail());
        booking.setDateCreated(LocalDateTime.now());
        booking.setStatus(BookingStatus.RESERVED);

        List<Passenger> passengers = request.passengers()
                .stream()
                .map(pRep->
                {
                    Passenger passenger = new Passenger();
                    passenger.setFirstName(pRep.firtName());
                    passenger.setLastName(pRep.lastName());
                    passenger.setSeatNumber(pRep.seatNumber());
                    passenger.setPassengerType(pRep.type());
                    passenger.setBooking(booking);
                    return passenger;
                })
                .toList();

            booking.setPassengers(passengers);

            Booking bookingSaved = bookingRepository.save(booking);

            return new BookingResponseDTO(
                    bookingSaved.getId(),
                    bookingSaved.getTrip().getId(),
                    bookingSaved.getFirstName(),
                    bookingSaved.getLastName(),
                    bookingSaved.getContactEmail(),
                    bookingSaved.getStatus()
            );
    }
}
