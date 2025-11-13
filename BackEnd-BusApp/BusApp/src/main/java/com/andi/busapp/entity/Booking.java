package com.andi.busapp.entity;

import com.andi.busapp.entity.enums.BookingStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private User user;
    private LocalDateTime dateCreated;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
