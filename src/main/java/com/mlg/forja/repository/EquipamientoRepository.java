package com.mlg.forja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlg.forja.modelo.Equipamiento;

public interface EquipamientoRepository extends JpaRepository<Equipamiento,Integer>
{
    Optional<Equipamiento> findByNombre(String nombre);
}
