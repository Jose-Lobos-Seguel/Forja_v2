package com.mlg.forja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlg.forja.modelo.EquipamientoRunaEntidad;
import com.mlg.forja.modelo.Runa;

public interface EquipamientoRunaRepository extends JpaRepository<EquipamientoRunaEntidad, Integer> 
{
    Optional<EquipamientoRunaEntidad> findByRuna(Runa runa);
}
