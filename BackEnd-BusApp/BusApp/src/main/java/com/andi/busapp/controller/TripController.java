package com.andi.busapp.controller;

import com.andi.busapp.dto.SeatStatus.SeatStatusDTO;
import com.andi.busapp.dto.TripDTO;
import com.andi.busapp.entity.City;
import com.andi.busapp.service.CityService;
import com.andi.busapp.service.SeatReservationService;
import com.andi.busapp.service.TripService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController
{

    private final TripService tripService;
    private final CityService cityService;
    private final SeatReservationService seatReservationService;
    public TripController(TripService tripService, CityService cityService, SeatReservationService seatReservationService) {
        this.tripService = tripService;
        this.cityService = cityService;
        this.seatReservationService = seatReservationService;
    }

    @GetMapping("/search")
    public List<TripDTO> searchTrips (
            @RequestParam String cityFromName,
            @RequestParam String cityToName,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
            )
    {
         City cityFromid = this.cityService.FindCityByName(cityFromName);
         City cityToid = this.cityService.FindCityByName(cityToName);

         return tripService.searchTrip(cityFromid.getId(),cityToid.getId(),date);

    }

    @GetMapping("/{tripId}/seats")
    public List<SeatStatusDTO> getSeatStatus (@PathVariable Long tripId)
    {
        return seatReservationService.getSeatStatusForTrip(tripId);
    }

}
