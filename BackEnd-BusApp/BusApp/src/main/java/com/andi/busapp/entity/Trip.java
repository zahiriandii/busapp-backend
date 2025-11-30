package com.andi.busapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private Long id;

    @ManyToOne
    private City cityFrom;
    @ManyToOne
    private City cityTo;
    private LocalDate departureDate;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Long price;

    @ManyToOne
    private Bus bus;
}
