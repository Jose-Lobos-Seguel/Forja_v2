package com.mlg.forja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlg.forja.modelo.Dimension;

public interface DimensionRepository extends JpaRepository<Dimension, Integer> {
}
