package com.andi.busapp.controller.AdminController;


import com.andi.busapp.dto.TripDTO;
import com.andi.busapp.dto.TripRequestDTO;
import com.andi.busapp.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/trips")
public class AdminTripController {

    private final TripService tripService;

    public AdminTripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public List<TripDTO> getAllTrips() {
        return tripService.getAllTrips();
    }

    @PostMapping
    public TripDTO createTrip(@RequestBody TripRequestDTO request) {
        return tripService.createTrip(request);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }
}

