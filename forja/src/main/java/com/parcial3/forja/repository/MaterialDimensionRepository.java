package com.parcial3.forja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parcial3.forja.model.Material;
import com.parcial3.forja.model.MaterialDimension;

public interface MaterialDimensionRepository extends JpaRepository<MaterialDimension, Integer> {
    List<MaterialDimension> findAllByMaterial(Material material);
}
