package com.andi.busapp.repository;

import com.andi.busapp.dto.AdminBookingDTO.AdminBookingResponseDTO;
import com.andi.busapp.dto.ResponseDTO.BookingResponseDTO;
import com.andi.busapp.entity.Booking;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>
{

    List<Booking> findByTripId(Long tripId);
    List<Booking> findAllByOrderByDateCreatedDesc();
}
