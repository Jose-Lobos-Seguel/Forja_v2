package com.forja.personal.DTO;

import java.util.List;

import lombok.Data;

@Data
public class EquipamientoDTORemoto {
    
    private Integer id;
    private String nombre;
    private Integer maxRunas;
    private Integer tipoId;
    private String tipoNombre;
    private List<Integer> materialDimensionIds;
    private List<Integer> runasIds;
    private Integer forjadorId;
    private String nombreForjador;

}