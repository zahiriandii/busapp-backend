package com.andi.busapp.entity;

import com.andi.busapp.entity.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "user-table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
