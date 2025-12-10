package com.andi.busapp.dto.AdminBookingDTO;

import com.andi.busapp.entity.enums.BookingStatus;

public record UpdateBookingStatusRequest(
        BookingStatus status
) {}
