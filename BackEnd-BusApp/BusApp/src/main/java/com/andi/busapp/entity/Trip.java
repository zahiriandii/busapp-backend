package com.andi.busapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
