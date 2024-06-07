package com.example.m295lb.repositories;

import com.example.m295lb.models.Pet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findPetByAlive(boolean alive);
    List<Pet> findPetByName(String name);


    @Transactional
    @Modifying
    @Query("DELETE FROM Pet p WHERE p.birthday = :date")
    int deletePetsByDate(@Param("date") LocalDate date);
}