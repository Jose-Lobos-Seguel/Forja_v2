package com.forja.recursos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.recursos.modelo.Material;
import com.forja.recursos.modelo.MaterialDimension;

public interface MaterialDimensionRepository extends JpaRepository<MaterialDimension, Integer> {
    List<MaterialDimension> findAllByMaterial(Material material);
    List<MaterialDimension> findByEquipamientoId(Integer equipamientoId);
}
