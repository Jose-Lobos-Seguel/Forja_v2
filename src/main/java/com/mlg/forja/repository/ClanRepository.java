package com.mlg.forja.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mlg.forja.modelo.Clan;

@Repository
public interface ClanRepository extends JpaRepository<Clan,Integer> {
    List<Clan> findByNombre(String nombre);

}
