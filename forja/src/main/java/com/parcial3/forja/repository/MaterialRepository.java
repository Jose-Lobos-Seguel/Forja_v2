package com.parcial3.forja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parcial3.forja.model.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    
}
