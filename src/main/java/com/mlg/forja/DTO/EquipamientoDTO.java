package com.mlg.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class EquipamientoDTO {
    
    //Atributos
    private Integer id;
    private String nombre;
    private String calidad;
    private Integer maxRunas;

    //Relaciones con otras clases
    private Integer tipoId;
    private String tipoNombre;
    private List<Integer> materialesIds;
    private List<Integer> runasIds;
    private Integer forjadorId;
    private String forjadorNombre;

}
