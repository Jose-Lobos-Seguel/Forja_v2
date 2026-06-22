package com.parcial3.forja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parcial3.forja.model.Equipamiento;

public interface EquipamientoRepository extends JpaRepository<Equipamiento,Integer>
{
    Optional<Equipamiento> findByNombre(String nombre);
}
