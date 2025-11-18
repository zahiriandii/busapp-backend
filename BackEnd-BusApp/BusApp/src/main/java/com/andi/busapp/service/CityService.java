package com.andi.busapp.service;

import com.andi.busapp.entity.City;

import java.util.List;

public interface CityService
{
    City findCityById(Long id);
    City FindCityByName(String name);
    List<City> findAllCities();
    City saveCity(City city);
    City updateCity(City city);
    void deleteCity(City city);

}
