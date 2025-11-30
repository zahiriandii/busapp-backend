package com.andi.busapp.entity;

import com.andi.busapp.entity.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seat_reservations",
  uniqueConstraints = {
        @UniqueConstraint(columnNames = {"trip_id","seat_id"})
  })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatReservation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;
}
