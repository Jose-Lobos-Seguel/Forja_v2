package com.forja.personal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.personal.modelo.Clan;

public interface ClanRepository extends JpaRepository<Clan,Integer> 
{
    List<Clan> findByNombre(String nombre);

}
