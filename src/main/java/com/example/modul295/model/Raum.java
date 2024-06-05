package com.example.modul295.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "raum")
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class Raum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

}
