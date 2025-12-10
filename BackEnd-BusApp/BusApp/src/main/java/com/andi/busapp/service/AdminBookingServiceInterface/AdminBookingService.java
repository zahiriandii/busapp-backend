package com.andi.busapp.service.AdminBookingServiceInterface;

import com.andi.busapp.dto.AdminBookingDTO.AdminBookingResponseDTO;
import com.andi.busapp.dto.AdminBookingDTO.UpdateBookingStatusRequest;
import com.andi.busapp.dto.ResponseDTO.BookingResponseDTO;
import com.andi.busapp.entity.enums.BookingStatus;

import java.util.List;

public interface AdminBookingService
{
    List<AdminBookingResponseDTO> getAllBookings();

    AdminBookingResponseDTO getBookingById(Long bookingId);

    AdminBookingResponseDTO updateStatus(Long bookingId, UpdateBookingStatusRequest request);

    void deleteBooking(Long bookingId);
}
