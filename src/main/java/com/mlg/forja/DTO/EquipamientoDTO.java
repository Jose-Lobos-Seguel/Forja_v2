package com.mlg.forja.DTO;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipamientoDTO {
    
    //Atributos
    private Integer id;
    private String nombre;
    private Integer maxRunas;

    //Relaciones con otras clases
    @NotNull
    private Integer tipoId;
    private String tipoNombre;
    private List<Integer> materialesIds;
    private List<Integer> runasIds;
    @NotNull
    private Integer forjadorId;
    private String forjadorNombre;

}
