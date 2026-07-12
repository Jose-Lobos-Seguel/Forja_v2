package com.forja.recursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.recursos.modelo.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    
}
