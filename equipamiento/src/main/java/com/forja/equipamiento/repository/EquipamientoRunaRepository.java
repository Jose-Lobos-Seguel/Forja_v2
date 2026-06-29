package com.forja.equipamiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.equipamiento.modelo.Equipamiento;
import com.forja.equipamiento.modelo.EquipamientoRunaEntidad;
import com.forja.equipamiento.modelo.Runa;

public interface EquipamientoRunaRepository extends JpaRepository<EquipamientoRunaEntidad, Integer> 
{
    List<EquipamientoRunaEntidad> findAllByRuna(Runa runa);
    List<EquipamientoRunaEntidad> findAllByEquipamiento(Equipamiento equipamiento);
}
