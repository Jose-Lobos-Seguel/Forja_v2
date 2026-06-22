package com.parcial3.personal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.parcial3.personal.model.Clan;

public interface ClanRepository extends JpaRepository<Clan,Integer> 
{
    List<Clan> findByNombre(String nombre);
}
