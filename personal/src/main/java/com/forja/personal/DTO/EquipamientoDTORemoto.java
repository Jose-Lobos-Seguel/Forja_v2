package com.forja.personal.DTO;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipamientoDTORemoto {
    
    private Integer id;
    private String nombre;
    @NotNull
    private Integer maxRunas;
    @NotNull
    private Integer tipoId;
    private String tipoNombre;
    private List<Integer> materialDimensionIds;
    private List<Integer> runasIds;
    @NotNull
    private Integer forjadorId;
    private String nombreForjador;

}