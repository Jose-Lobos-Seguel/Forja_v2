package com.forja.personal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.personal.modelo.Region;

public interface RegionRepository extends JpaRepository<Region,Integer>
{
    List<Region> findByNombre(String nombre);
}
