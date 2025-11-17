package com.andi.busapp.exceptions;

public class SeatAlreadyReservedException extends RuntimeException {
    public SeatAlreadyReservedException(String seatNumber) {
        super("Seat " + seatNumber + " is already reserved");
    }
}
