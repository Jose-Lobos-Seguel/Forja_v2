package com.mlg.forja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlg.forja.modelo.EquipamientoRunaEntidad;

@Repository
public interface EquipamientoRunaRepository extends JpaRepository<EquipamientoRunaEntidad, Integer> 
{

}
