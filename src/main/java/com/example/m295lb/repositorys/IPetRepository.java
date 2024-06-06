package com.example.m295lb.repositorys;

import com.example.m295lb.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPetRepository extends JpaRepository<Pet, Integer> {
}
