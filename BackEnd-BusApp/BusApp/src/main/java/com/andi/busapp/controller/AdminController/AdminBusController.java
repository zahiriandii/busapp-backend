package com.andi.busapp.controller.AdminController;


import com.andi.busapp.dto.Bus.BusCreateUpdateDTO;
import com.andi.busapp.dto.Bus.BusResponseDTO;
import com.andi.busapp.service.BusService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/buses")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminBusController {

    private final BusService busService;

    public AdminBusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public List<BusResponseDTO> getAll() {
        return busService.getAll();
    }

    @GetMapping("/{id}")
    public BusResponseDTO getById(@PathVariable Long id) {
        return busService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusResponseDTO create(@RequestBody BusCreateUpdateDTO request) {
        return busService.create(request);
    }

    @PutMapping("/{id}")
    public BusResponseDTO update(@PathVariable Long id,
                                 @RequestBody BusCreateUpdateDTO request) {
        return busService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        busService.delete(id);
    }
}
