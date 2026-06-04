package com.mlg.forja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlg.forja.modelo.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    // Ejemplo: Buscar materiales asociados a un equipamiento específico
    List<Material> findByEquipamientoId(Integer equipamientoId);
}
