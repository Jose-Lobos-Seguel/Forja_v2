package com.parcial3.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parcial3.personal.model.Clan;
import com.parcial3.personal.model.Enano;

public interface EnanoRepository extends JpaRepository<Enano, Integer> {
    List<Enano> findByNombre(String nombre);

    List<Enano> findByClan(Clan clan);
}
