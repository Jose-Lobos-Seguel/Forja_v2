package com.mlg.forja.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mlg.forja.modelo.Region;

public interface RegionRepository extends JpaRepository<Region,Integer>
{
    List<Region> findByNombre(String nombre);
}
