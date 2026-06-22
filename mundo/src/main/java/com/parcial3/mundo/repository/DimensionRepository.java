package com.parcial3.mundo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parcial3.mundo.model.Dimension;

public interface DimensionRepository extends JpaRepository<Dimension, Integer> {
    // Puedes añadir búsquedas por nombre si lo necesitas
    Dimension findByNombre(String nombre);
}
