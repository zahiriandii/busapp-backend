package com.andi.busapp.service;

import com.andi.busapp.dto.RequestDTO.CreateBookingRequest;
import com.andi.busapp.dto.ResponseDTO.BookingResponseDTO;
import com.andi.busapp.entity.Booking;

import java.util.List;

public interface BookingService
{
    public Booking save(Booking booking);
    public List<Booking> findAll();
    public Booking findById(Long id);
    public void deleteById(Long id);
    public BookingResponseDTO createBooking(CreateBookingRequest request);
}
