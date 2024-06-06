package com.example.m295lb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "owner")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerID")
    private int ownerID;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;
}