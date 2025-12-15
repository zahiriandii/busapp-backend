package com.andi.busapp.service.Implementation;

import com.andi.busapp.dto.TripDTO;
import com.andi.busapp.entity.*;
import com.andi.busapp.entity.enums.SeatStatus;
import com.andi.busapp.repository.SeatReservationRepository;
import com.andi.busapp.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {

    @Mock TripRepository tripRepository;
    @Mock SeatReservationRepository seatReservationRepository;

    @InjectMocks TripServiceImpl tripService;

    private Trip createTrip(int seatsCount, long reservedSeats) {
        City from = new City();
        from.setName("Skopje");

        City to = new City();
        to.setName("Pristina");

        Bus bus = new Bus();
        bus.setId(1L);

        List<Seat> seats = java.util.stream.IntStream.rangeClosed(1, seatsCount)
                .mapToObj(i -> {
                    Seat s = new Seat();
                    s.setId((long) i);
                    s.setSeatNumber("S" + i);
                    s.setBus(bus);
                    return s;
                })
                .toList();
        bus.setSeats(seats);

        Trip trip = new Trip();
        trip.setId(10L);
        trip.setCityFrom(from);
        trip.setCityTo(to);
        trip.setBus(bus);
        trip.setDepartureTime(LocalDateTime.of(2025, 1, 1, 10, 0));
        trip.setArrivalTime(LocalDateTime.of(2025, 1, 1, 12, 0));
        trip.setPrice(500L);

        when(seatReservationRepository.countByTripAndStatus(trip, SeatStatus.RESERVED))
                .thenReturn(reservedSeats);

        return trip;
    }

    @Test
    void searchTrip_returnsTripDTOWithAvailableSeats() {
        Long cityFromId = 1L;
        Long cityToId = 2L;
        LocalDate date = LocalDate.of(2025, 1, 1);

        Trip trip = createTrip(40, 5); // 40 total, 5 reserved → 35 available

        when(tripRepository.findByCityFromIdAndCityToIdAndDepartureDate(cityFromId, cityToId, date))
                .thenReturn(List.of(trip));

        // when
        List<TripDTO> result = tripService.searchTrip(cityFromId, cityToId, date);

        // then
        assertEquals(1, result.size());
        TripDTO dto = result.get(0);

        assertEquals(trip.getId(), dto.tripId());
        assertEquals("Skopje", dto.cityFrom());
        assertEquals("Pristina", dto.cityTo());
        assertEquals(35, dto.seats());
        assertEquals(500L, dto.price());
        assertEquals(trip.getDepartureTime(), dto.departureTime());
        assertEquals(trip.getArrivalTime(), dto.arrivalTime());
    }

    @Test
    void searchTrip_noTrips_returnsEmptyList() {
        Long cityFromId = 1L;
        Long cityToId = 2L;
        LocalDate date = LocalDate.now();

        when(tripRepository.findByCityFromIdAndCityToIdAndDepartureDate(cityFromId, cityToId, date))
                .thenReturn(List.of());

        List<TripDTO> result = tripService.searchTrip(cityFromId, cityToId, date);

        assertTrue(result.isEmpty());
    }
}
