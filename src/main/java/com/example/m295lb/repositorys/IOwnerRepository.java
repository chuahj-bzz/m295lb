package com.example.m295lb.repositorys;

import com.example.m295lb.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOwnerRepository extends JpaRepository<Owner, Integer> {

    List<Owner> findByActive(boolean active);

    List<Owner> findByFirstname(String firstName);
}