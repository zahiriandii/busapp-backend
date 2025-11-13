package com.andi.busapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Bus
{
    @Id
    private int Id;
    private String BusName;
    private String plateNumber;

}
