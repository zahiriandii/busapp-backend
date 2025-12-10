package com.andi.busapp.service;

import com.andi.busapp.dto.Seat.SeatLayout;
import com.andi.busapp.dto.Seat.SeatResponseDTO;

import java.util.List;

public interface SeatService
{
    List<SeatResponseDTO> getSeatsForBus(Long busId);

    List<SeatResponseDTO> generateLayoutForBus(Long busId, SeatLayout request);
}
