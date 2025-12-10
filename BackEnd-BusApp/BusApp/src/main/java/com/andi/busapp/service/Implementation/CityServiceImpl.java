package com.andi.busapp.service.Implementation;

// package com.andi.busticket.admin.service.impl;


import com.andi.busapp.dto.CityDTO.CityCreateUpdateDto;
import com.andi.busapp.dto.CityDTO.CityResponseDto;
import com.andi.busapp.entity.City;
import com.andi.busapp.repository.CityRepository;
import com.andi.busapp.service.CityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<CityResponseDto> getAll() {
        return cityRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CityResponseDto getById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + id));
        return toDto(city);
    }

    @Override
    public CityResponseDto create(CityCreateUpdateDto request) {
        // optional: validate unique name
        if (cityRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("City with name already exists: " + request.name());
        }

        City city = new City();
        city.setName(request.name());
        city.setCountryCode(request.countryCode()); // uses getCountryCode/setCountryCode based on "CountryCode" field

        City saved = cityRepository.save(city);
        return toDto(saved);
    }

    @Override
    public CityResponseDto update(Long id, CityCreateUpdateDto request) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + id));

        city.setName(request.name());
        city.setCountryCode(request.countryCode());

        City saved = cityRepository.save(city);
        return toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new IllegalArgumentException("City not found: " + id);
        }
        cityRepository.deleteById(id);
    }

    @Override
    public City FindCityByName(String name) {
        return this.cityRepository.findCityByName(name);
    }

    private CityResponseDto toDto(City entity) {
        return new CityResponseDto(
                entity.getId(),           // field "Id" -> getter getId()
                entity.getName(),
                entity.getCountryCode()   // field "CountryCode" -> getter getCountryCode()
        );
    }
}
