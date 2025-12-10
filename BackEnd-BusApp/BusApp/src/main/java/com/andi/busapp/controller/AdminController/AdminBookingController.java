package com.andi.busapp.controller.AdminController;

import com.andi.busapp.dto.AdminBookingDTO.AdminBookingResponseDTO;
import com.andi.busapp.dto.AdminBookingDTO.UpdateBookingStatusRequest;
import com.andi.busapp.entity.enums.BookingStatus;
import com.andi.busapp.service.AdminBookingServiceInterface.AdminBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bookings")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminBookingController {

    private final AdminBookingService bookingService;

    public AdminBookingController(AdminBookingService bookingService) {
        this.bookingService = bookingService;
    }

    // GET /api/admin/bookings
    @GetMapping
    public List<AdminBookingResponseDTO> getAll() {
        return bookingService.getAllBookings();
    }

    // GET /api/admin/bookings/{id}
    @GetMapping("/{id}")
    public AdminBookingResponseDTO getOne(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    // PATCH /api/admin/bookings/{id}/status
    @PatchMapping("/{id}/status")
    public AdminBookingResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateBookingStatusRequest request
    ) {
        return bookingService.updateStatus(id, request);
    }

    // DELETE /api/admin/bookings/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}