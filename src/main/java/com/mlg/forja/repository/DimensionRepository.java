package com.mlg.forja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlg.forja.modelo.Dimension;

@Repository
public interface DimensionRepository extends JpaRepository<Dimension, Integer> {
    // Puedes añadir búsquedas por nombre si lo necesitas
    Dimension findByNombre(String nombre);
}
