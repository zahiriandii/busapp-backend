package com.andi.busapp.service;

import com.andi.busapp.dto.SeatStatus.SeatStatusDTO;
import com.andi.busapp.entity.SeatReservation;

import java.util.List;

public interface SeatReservationService
{
    SeatReservation reserveSeat (Long tripId,Long seatId, Long bookingId,Long passengerId);
    List<SeatStatusDTO> getSeatStatusForTrip(Long tripId);
}
