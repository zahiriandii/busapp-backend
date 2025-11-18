package com.andi.busapp.controller;

import com.andi.busapp.dto.TripDTO;
import com.andi.busapp.entity.City;
import com.andi.busapp.service.CityService;
import com.andi.busapp.service.TripService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController
{

    private final TripService tripService;
    private final CityService cityService;
    public TripController(TripService tripService, CityService cityService) {
        this.tripService = tripService;
        this.cityService = cityService;
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
}
