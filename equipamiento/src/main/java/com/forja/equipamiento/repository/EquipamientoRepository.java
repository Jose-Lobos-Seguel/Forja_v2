package com.forja.equipamiento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.equipamiento.modelo.Equipamiento;

public interface EquipamientoRepository extends JpaRepository<Equipamiento,Integer>
{
    Optional<Equipamiento> findByNombre(String nombre);
}
