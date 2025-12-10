package com.andi.busapp.service.AdminBookingServiceInterface.ServiceImpl;

import com.andi.busapp.dto.AdminBookingDTO.AdminBookingResponseDTO;
import com.andi.busapp.dto.AdminBookingDTO.PassengerDTO;
import com.andi.busapp.dto.AdminBookingDTO.UpdateBookingStatusRequest;
import com.andi.busapp.dto.ResponseDTO.BookingResponseDTO;
import com.andi.busapp.entity.Booking;
import com.andi.busapp.entity.Passenger;
import com.andi.busapp.entity.enums.BookingStatus;
import com.andi.busapp.repository.BookingRepository;
import com.andi.busapp.service.AdminBookingServiceInterface.AdminBookingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminBookingService
{
    private final BookingRepository bookingRepository;

    public AdminServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<AdminBookingResponseDTO> getAllBookings() {
        return bookingRepository.findAllByOrderByDateCreatedDesc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public AdminBookingResponseDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));
        return toDto(booking);
    }

    @Override
    public AdminBookingResponseDTO updateStatus(Long bookingId, UpdateBookingStatusRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));

        booking.setStatus(request.status());
        Booking saved = bookingRepository.save(booking);

        return toDto(saved);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }
        bookingRepository.deleteById(bookingId);
    }

    // --- mapping helpers ---

    private AdminBookingResponseDTO toDto(Booking booking) {
        Long tripId = booking.getTrip() != null ? booking.getTrip().getId() : null;

        List<PassengerDTO> passengerDtos = booking.getPassengers() == null
                ? List.of()
                : booking.getPassengers()
                .stream()
                .map(this::toPassengerDto)
                .toList();

        return new AdminBookingResponseDTO(
                booking.getId(),
                tripId,
                booking.getFirstName(),
                booking.getLastName(),
                booking.getContactEmail(),
                booking.getStatus(),
                passengerDtos
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
