package com.andi.busapp.service.Implementation;

import com.andi.busapp.dto.TripDTO;
import com.andi.busapp.dto.TripRequestDTO;
import com.andi.busapp.entity.Bus;
import com.andi.busapp.entity.City;
import com.andi.busapp.entity.Trip;
import com.andi.busapp.entity.enums.SeatStatus;
import com.andi.busapp.repository.BusRepository;
import com.andi.busapp.repository.CityRepository;
import com.andi.busapp.repository.SeatReservationRepository;
import com.andi.busapp.repository.TripRepository;
import com.andi.busapp.service.CityService;
import com.andi.busapp.service.TripService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService
{
    private final TripRepository tripRepository;
    private final SeatReservationRepository seatReservationRepository;
    private final CityRepository cityRepository;
    private final BusRepository busRepository;
    public TripServiceImpl(TripRepository tripRepository, SeatReservationRepository seatReservationRepository, CityRepository cityRepository, BusRepository busRepository) {
        this.tripRepository = tripRepository;
        this.seatReservationRepository = seatReservationRepository;
        this.cityRepository = cityRepository;
        this.busRepository = busRepository;
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

    @Override
    public List<TripDTO> getAllTrips() {
        List<Trip> trips = this.tripRepository.findAll();

        return trips.stream()
                .map(this::toTripDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TripDTO createTrip(TripRequestDTO request) {
        City from = cityRepository.getById(request.cityFromId());
        City to = cityRepository.getById(request.cityToId());
        Bus bus = busRepository.getById(request.busId());

        Trip trip = new Trip();
        trip.setCityFrom(from);
        trip.setCityTo(to);
        trip.setDepartureDate(request.departureDate());
        trip.setDepartureTime(request.departureTime());
        trip.setArrivalTime(request.arrivalTime());
        trip.setDepartureDate(request.departureTime().toLocalDate());
        trip.setPrice(request.price());
        trip.setBus(bus);

        Trip saved = tripRepository.save(trip);

        return new TripDTO(
                saved.getId(),
                saved.getCityFrom().getName(),
                saved.getCityTo().getName(),
                saved.getDepartureTime(),
                saved.getArrivalTime(),
                saved.getBus().getSeats().size(),
                saved.getPrice()
        );
    }

    private TripDTO toTripDTO(Trip trip) {
        int totalSeats = trip.getBus().getSeats().size();
        long reservedSeats = this.seatReservationRepository.countByTripAndStatus(trip, SeatStatus.RESERVED);
        int availableSeats = (int) (totalSeats - reservedSeats);
        return new TripDTO(
          trip.getId(),
          trip.getCityFrom().getName(),
          trip.getCityTo().getName(),
          trip.getDepartureTime(),
          trip.getArrivalTime(),
          availableSeats,
          trip.getPrice()
        );
    }
}
