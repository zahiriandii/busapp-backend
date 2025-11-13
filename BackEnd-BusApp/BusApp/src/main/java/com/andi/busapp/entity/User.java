package com.andi.busapp.entity;

import com.andi.busapp.entity.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "user-table")
public class User
{
    @Id
    private int Id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private Date BirthDate;
    private String Gender;
    private String PhoneNumber;
    private UserType UserType;

}
