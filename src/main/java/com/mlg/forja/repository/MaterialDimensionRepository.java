package com.mlg.forja.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlg.forja.modelo.MaterialDimension;

@Repository
public interface MaterialDimensionRepository extends JpaRepository<MaterialDimension, Integer> {
    // Buscar todas las dimensiones donde aparece un material específico
    List<MaterialDimension> findByMaterialId(Integer materialId);
    
    // Buscar todos los materiales que existen en una dimensión específica
    List<MaterialDimension> findByDimensionId(Integer dimensionId);
    
    // Buscar la pureza específica de un material en una dimensión
    Optional<MaterialDimension> findByMaterialIdAndDimensionId(Integer materialId, Integer dimensionId);
}
