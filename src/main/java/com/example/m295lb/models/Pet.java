package com.example.m295lb.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pet")
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private int id_pet;

    @Column(name = "name")
    private String name;

    @Column(name = "breed")
    private String breed;

    @Column(name = "weight")
    private double weight;
}