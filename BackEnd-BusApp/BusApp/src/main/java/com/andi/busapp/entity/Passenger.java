package com.andi.busapp.entity;

import com.andi.busapp.entity.enums.PassengerType;
import jakarta.persistence.*;

@Entity
public class Passenger
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String FirstName;
    private String LastName;

    @Enumerated(EnumType.STRING)
    private PassengerType PassengerType;
    private String SeatNumber;
}
