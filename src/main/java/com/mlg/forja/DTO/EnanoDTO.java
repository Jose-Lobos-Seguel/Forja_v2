package com.mlg.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class EnanoDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String titulo;
    private String especialidad;

    //Relacion con otras clases
    private Integer clanId;
    private String clanNombre;
    private List<Integer> equipamientosForjadosIds;
}
