package com.mlg.forja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlg.forja.modelo.Runa;

@Repository
public interface RunaRepository extends JpaRepository<Runa,Integer> {

}
