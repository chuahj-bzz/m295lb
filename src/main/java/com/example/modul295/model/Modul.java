package com.example.modul295.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "modul")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Modul {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    private String name;
    @FutureOrPresent
    private LocalDateTime start;
    @Future
    private LocalDateTime end;
    private double cost;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Raum raum;
}
