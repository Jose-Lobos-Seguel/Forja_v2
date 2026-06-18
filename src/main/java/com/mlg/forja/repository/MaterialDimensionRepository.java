package com.mlg.forja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlg.forja.modelo.Material;
import com.mlg.forja.modelo.MaterialDimension;

@Repository
public interface MaterialDimensionRepository extends JpaRepository<MaterialDimension, Integer> {
    List<MaterialDimension> findAllByMaterial(Material material);
}
