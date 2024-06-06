package com.example.m295lb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "owner")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_owner")
    private int id_owner;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "firstname")
    private String firstname;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "lastname")
    private String lastname;

    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @Past(message = "Birthday must be in the past")
    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "active")
    private boolean active;

    @Min(value = 0, message = "Number of pets cannot be less than 0")
    @Column(name = "num_pets")
    private int num_pets;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}