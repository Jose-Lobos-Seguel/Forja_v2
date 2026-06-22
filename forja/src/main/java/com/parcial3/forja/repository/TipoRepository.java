package com.parcial3.forja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parcial3.forja.model.Tipo;

public interface TipoRepository extends JpaRepository<Tipo,Integer>{

    List<Tipo> findByNombre(String nombre);

}
