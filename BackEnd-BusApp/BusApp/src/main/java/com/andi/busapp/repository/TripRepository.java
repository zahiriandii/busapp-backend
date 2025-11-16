package com.andi.busapp.repository;

import com.andi.busapp.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>
{
    List<Trip> findByCityFromIdAndCityToIdAndDepartureDate(Long cityFromId, Long cityToId, LocalDate departureDate);
}
