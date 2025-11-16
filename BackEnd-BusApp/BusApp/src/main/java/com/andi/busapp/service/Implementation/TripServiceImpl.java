package com.andi.busapp.service.Implementation;

import com.andi.busapp.dto.TripDTO;
import com.andi.busapp.entity.Trip;
import com.andi.busapp.repository.TripRepository;
import com.andi.busapp.service.TripService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService
{
    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }


    @Override
    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public Trip findTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip updateTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    @Override
    public List<TripDTO> searchTrip(Long cityFromId, Long cityToId, LocalDate departureDate) {

        List<Trip> trips = this.tripRepository
                .findByCityFromIdAndCityToIdAndDepartureDate(cityFromId,cityToId,departureDate);

        return trips.stream()
                .map(this::toTripDTO)
                .collect(Collectors.toList());
    }

    private TripDTO toTripDTO(Trip trip) {
        int availableSeats = trip.getTotalSeats();
        return new TripDTO(
          trip.getId(),
          trip.getCityFrom().getName(),
          trip.getCityTo().getName(),
          trip.getDepartureTime(),
          trip.getArrivalTime(),
          trip.getTotalSeats(),
          trip.getPrice()
        );
    }
}
