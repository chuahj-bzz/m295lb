package com.example.m295lb.repositorys;

import com.example.m295lb.models.Pet;
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

    @Modifying
    @Query("delete from Pet p where p.birthday = :date")
    void deletePetsByDate(@Param("date") LocalDate date);
}