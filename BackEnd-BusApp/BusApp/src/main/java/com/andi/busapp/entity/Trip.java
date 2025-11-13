package com.andi.busapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Trip
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private City cityFrom;
    @ManyToOne
    private City cityTo;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Long price;
    private int totalSeats;
}
