package com.example.modul295.repository;

import com.example.modul295.model.Raum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRaumRepository extends JpaRepository<Raum, Integer> {
}
