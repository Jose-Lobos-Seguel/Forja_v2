package com.parcial3.forja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parcial3.forja.model.EquipamientoRunaEntidad;
import com.parcial3.forja.model.Runa;

public interface EquipamientoRunaRepository extends JpaRepository<EquipamientoRunaEntidad, Integer> 
{
    Optional<EquipamientoRunaEntidad> findByRuna(Runa runa);
}
