package com.mlg.forja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlg.forja.modelo.Material;


public interface MaterialRepository extends JpaRepository<Material, Integer> {
    
}
