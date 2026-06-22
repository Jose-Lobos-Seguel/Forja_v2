package com.mlg.forja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlg.forja.modelo.Material;
import com.mlg.forja.modelo.MaterialDimension;


public interface MaterialDimensionRepository extends JpaRepository<MaterialDimension, Integer> {
    List<MaterialDimension> findAllByMaterial(Material material);
}
