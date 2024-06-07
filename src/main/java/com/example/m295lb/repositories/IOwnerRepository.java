package com.example.m295lb.repositories;

import com.example.m295lb.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOwnerRepository extends JpaRepository<Owner, Integer> {
}