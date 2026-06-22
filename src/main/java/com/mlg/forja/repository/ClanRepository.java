package com.mlg.forja.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mlg.forja.modelo.Clan;

public interface ClanRepository extends JpaRepository<Clan,Integer> 
{
    List<Clan> findByNombre(String nombre);

}
