package com.mlg.forja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlg.forja.modelo.Clan;
import com.mlg.forja.modelo.Enano;

@Repository
public interface EnanoRepository extends JpaRepository<Enano, Integer> {

    List<Enano> findByNombre(String nombre);

    List<Enano> findByClan(Clan clan);
}
