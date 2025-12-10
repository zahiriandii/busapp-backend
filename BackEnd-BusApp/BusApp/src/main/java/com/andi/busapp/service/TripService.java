package com.andi.busapp.service;

import com.andi.busapp.dto.TripDTO;
import com.andi.busapp.dto.TripRequestDTO;
import com.andi.busapp.entity.Trip;

import java.time.LocalDate;
import java.util.List;

public interface TripService
{
    public List<Trip> findAllTrips();
    public Trip findTripById(Long id);
    public Trip saveTrip(Trip trip);
    public Trip updateTrip(Trip trip);
    public void deleteTrip(Long id);
    public List<TripDTO> searchTrip(Long cityFromId, Long cityToId, LocalDate departureDate);

    List<TripDTO> getAllTrips();

    TripDTO createTrip(TripRequestDTO request);
}
