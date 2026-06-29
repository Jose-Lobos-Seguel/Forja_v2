package com.forja.equipamiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forja.equipamiento.modelo.Tipo;

public interface TipoRepository extends JpaRepository<Tipo,Integer>{

    List<Tipo> findByNombre(String nombre);

}
