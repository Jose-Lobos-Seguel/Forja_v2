package com.forja.recursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.recursos.modelo.Dimension;

public interface DimensionRepository extends JpaRepository<Dimension, Integer> {
}
