package com.andi.busapp.service.Implementation;

import com.andi.busapp.entity.City;
import com.andi.busapp.repository.CityRepository;
import com.andi.busapp.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService
{
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City findCityById(Long id) {
        return this.cityRepository.findById(id).orElse(null);
    }

    @Override
    public City FindCityByName(String Name) {
        return this.cityRepository.findCityByName(Name);
    }

    @Override
    public List<City> findAllCities() {
        return this.cityRepository.findAll();
    }

    @Override
    public City saveCity(City city) {
        return this.cityRepository.save(city);
    }

    @Override
    public City updateCity(City city) {
        return this.cityRepository.save(city);
    }

    @Override
    public void deleteCity(City city) {
        this.cityRepository.delete(city);
    }
}
