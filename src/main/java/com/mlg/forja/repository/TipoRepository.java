package com.mlg.forja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mlg.forja.modelo.Tipo;

@Repository
public interface TipoRepository extends JpaRepository<Tipo,Integer>{

    List<Tipo> findByNombre(String nombre);

}
