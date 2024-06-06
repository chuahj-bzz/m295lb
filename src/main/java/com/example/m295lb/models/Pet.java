package com.example.m295lb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pet")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petID")
    private int petID;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Breed is mandatory")
    @Column(name = "breed")
    private String breed;

    @PositiveOrZero(message = "Weight should be positive or zero")
    @Column(name = "weight")
    private double weight;

    @PastOrPresent(message = "Birthday should be in the past or present")
    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "alive")
    private boolean alive;

    @Min(value = 0, message = "Legs should be positive or zero")
    @Max(value = 100, message = "Legs should not be more than 100")
    @Column(name = "legs")
    private int legs;

 //   @ManyToOne
 //   @JoinColumn(name = "owner_ownerID", nullable = false)
 //   private Owner owner;
}