package com.andi.busapp.service.Implementation;

import com.andi.busapp.dto.Seat.SeatLayout;
import com.andi.busapp.dto.Seat.SeatResponseDTO;
import com.andi.busapp.entity.Bus;
import com.andi.busapp.entity.Seat;
import com.andi.busapp.repository.BusRepository;
import com.andi.busapp.repository.SeatRepository;
import com.andi.busapp.service.SeatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService
{

    private final SeatRepository seatRepository;
    private final BusRepository busRepository;

    public SeatServiceImpl(SeatRepository seatRepository, BusRepository busRepository) {
        this.seatRepository = seatRepository;
        this.busRepository = busRepository;
    }

    @Override
    public List<SeatResponseDTO> getSeatsForBus(Long busId) {
        return seatRepository.findByBusIdOrderBySeatNumberAsc(busId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<SeatResponseDTO> generateLayoutForBus(Long busId, SeatLayout request) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found: " + busId));

        // clear old seats for that bus
        seatRepository.deleteByBusId(busId);

        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= request.seatRows(); row++) {
            for (int col = 1; col <= request.seatsPerRow(); col++) {
                char columnLetter = (char) ('A' + (col - 1)); // A, B, C, ...
                String seatNumber = row + String.valueOf(columnLetter); // e.g. "1A"

                Seat seat = new Seat();
                seat.setBus(bus);
                seat.setSeatNumber(seatNumber);

                seats.add(seat);
            }
        }

        List<Seat> saved = seatRepository.saveAll(seats);
        return saved.stream()
                .map(this::toDto)
                .toList();
    }

    private SeatResponseDTO toDto(Seat seat) {
        return new SeatResponseDTO(
                seat.getId(),
                seat.getBus().getId(),
                seat.getSeatNumber()
        );
    }
}
