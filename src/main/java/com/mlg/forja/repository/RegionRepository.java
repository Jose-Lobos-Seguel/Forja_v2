package com.mlg.forja.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mlg.forja.modelo.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region,Integer>
{
    List<Region> findByNombre(String nombre);
}
