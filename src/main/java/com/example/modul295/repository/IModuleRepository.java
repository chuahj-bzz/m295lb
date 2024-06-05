package com.example.modul295.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.modul295.model.Modul;

@Repository
public interface IModuleRepository extends JpaRepository<Modul, Integer> {
}

