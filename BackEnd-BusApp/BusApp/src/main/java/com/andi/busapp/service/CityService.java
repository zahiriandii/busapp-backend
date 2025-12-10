package com.andi.busapp.service;

import com.andi.busapp.dto.CityDTO.CityCreateUpdateDto;
import com.andi.busapp.dto.CityDTO.CityResponseDto;
import com.andi.busapp.entity.City;

import java.util.List;

public interface CityService
{
    List<CityResponseDto> getAll();

    CityResponseDto getById(Long id);

    CityResponseDto create(CityCreateUpdateDto request);

    CityResponseDto update(Long id, CityCreateUpdateDto request);

    void delete(Long id);
    City FindCityByName(String name);
}
