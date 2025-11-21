package com.andi.busapp.entity;

import com.andi.busapp.entity.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String country;

    @Enumerated(EnumType.STRING)
    private UserType userType;

}
