package com.andi.busapp.controller;

import com.andi.busapp.dto.BookingTicketInfo;
import com.andi.busapp.dto.RequestDTO.CreateBookingRequest;
import com.andi.busapp.dto.ResponseDTO.BookingResponseDTO;
import com.andi.busapp.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController
{
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponseDTO createBooking (@RequestBody CreateBookingRequest createBookingRequest)
    {
        return this.bookingService.createBooking(createBookingRequest);
    }

    @GetMapping("/{id}")
    public BookingTicketInfo findBooking (@PathVariable Long id)
    {
        return this.bookingService.findById(id);
    }


}
