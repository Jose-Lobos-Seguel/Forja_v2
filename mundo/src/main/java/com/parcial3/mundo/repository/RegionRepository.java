package com.parcial3.mundo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.parcial3.mundo.model.Region;

public interface RegionRepository extends JpaRepository<Region,Integer>
{
    List<Region> findByNombre(String nombre);
}
