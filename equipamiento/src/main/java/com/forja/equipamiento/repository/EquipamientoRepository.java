package com.forja.equipamiento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.equipamiento.modelo.Equipamiento;
import java.util.List;


public interface EquipamientoRepository extends JpaRepository<Equipamiento,Integer>
{
    Optional<Equipamiento> findByNombre(String nombre);
    List<Equipamiento> findAllByForjador_id(Integer forjador_id);
}
