package com.andi.busapp.repository;

import com.andi.busapp.dto.SeatStatus.SeatStatusDTO;
import com.andi.busapp.entity.Seat;
import com.andi.busapp.entity.SeatReservation;
import com.andi.busapp.entity.Trip;
import com.andi.busapp.entity.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long>
{
    boolean existsByTripAndSeat(Trip trip, Seat seat);

    @Query("""
        select new com.andi.busapp.dto.SeatStatus.SeatStatusDTO(
            s.id,
            s.seatNumber,
            case when sr.id is null then false else true end
        )
        from Seat s
        join s.bus b
        join Trip t on t.bus = b
        left join SeatReservation sr
               on sr.trip = t
              and sr.seat = s
              and sr.status <> com.andi.busapp.entity.enums.SeatStatus.CANCELLED
        where t.id = :tripId
        order by s.seatNumber
        """)
    List<SeatStatusDTO> getSeatStatusByTrip(@Param("tripId") Long tripId);

    List<SeatReservation> findByTripAndSeatIn(Trip trip, List<Seat> seats);

    long countByTripAndStatus(Trip trip, SeatStatus status);
}
