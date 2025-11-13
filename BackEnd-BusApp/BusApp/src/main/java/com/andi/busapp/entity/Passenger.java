package com.andi.busapp.entity;

import com.andi.busapp.entity.enums.PassengerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String FirstName;
    private String LastName;

    @ManyToOne
    @JoinColumn(name = "booking_Id", nullable = false)
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private PassengerType PassengerType;
    private String SeatNumber;
}
