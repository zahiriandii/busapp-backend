package com.andi.busapp.exceptions;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Long BookingId) {
        super("Booking with id " + BookingId + " not found");
    }
}
