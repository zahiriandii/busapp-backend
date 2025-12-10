package com.andi.busapp.controller.AdminController;

// package com.andi.busticket.admin.controller;



import com.andi.busapp.dto.Seat.SeatLayout;
import com.andi.busapp.dto.Seat.SeatResponseDTO;
import com.andi.busapp.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/seats")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminSeatController {

    private final SeatService seatService;

    public AdminSeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // GET /api/admin/seats/bus/1
    @GetMapping("/bus/{busId}")
    public List<SeatResponseDTO> getSeatsForBus(@PathVariable Long busId) {
        return seatService.getSeatsForBus(busId);
    }

    // POST /api/admin/seats/bus/1/generate
    @PostMapping("/bus/{busId}/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public List<SeatResponseDTO> generateLayout(
            @PathVariable Long busId,
            @RequestBody SeatLayout request
    ) {
        return seatService.generateLayoutForBus(busId, request);
    }
}
