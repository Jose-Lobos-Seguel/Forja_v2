package com.forja.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.personal.modelo.Clan;
import com.forja.personal.modelo.Enano;

public interface EnanoRepository extends JpaRepository<Enano, Integer> {

    List<Enano> findByNombre(String nombre);

    List<Enano> findByClan(Clan clan);
}
