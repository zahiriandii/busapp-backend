package com.andi.busapp.controller.AdminController;



// package com.andi.busticket.admin.controller;

import com.andi.busapp.dto.CityDTO.CityCreateUpdateDto;
import com.andi.busapp.dto.CityDTO.CityResponseDto;
import com.andi.busapp.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cities")
//@PreAuthorize("hasRole('ADMIN')")
public class CityAdminController {

    private final CityService cityService;

    public CityAdminController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<CityResponseDto> getAll() {
        return cityService.getAll();
    }

    @GetMapping("/{id}")
    public CityResponseDto getById(@PathVariable Long id) {
        return cityService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponseDto create(@RequestBody CityCreateUpdateDto request) {
        return cityService.create(request);
    }

    @PutMapping("/{id}")
    public CityResponseDto update(@PathVariable Long id,
                                  @RequestBody CityCreateUpdateDto request) {
        return cityService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cityService.delete(id);
    }
}

