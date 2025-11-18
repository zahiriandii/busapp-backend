package com.andi.busapp.controller;

import com.andi.busapp.dto.RequestDTO.CreateBookingRequest;
import com.andi.busapp.dto.ResponseDTO.BookingResponseDTO;
import com.andi.busapp.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController
{
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public BookingResponseDTO createBooking (@RequestBody CreateBookingRequest createBookingRequest)
    {
        return this.bookingService.createBooking(createBookingRequest);
    }

}
