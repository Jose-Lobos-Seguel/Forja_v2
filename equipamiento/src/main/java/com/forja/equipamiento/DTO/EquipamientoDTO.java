package com.forja.equipamiento.DTO;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipamientoDTO {
    
    private Integer id;
    private String nombre;
    @NotNull
    private Integer maxRunas;
    @NotNull
    private Integer tipoId;
    private String tipoNombre;
    private List<Integer> materialDimensionIds;//IMPORTANTE!!! aqui entra el id de la RELACION MaterialDimension ya que es la relacion la que contiene la pureza del material
    private List<Integer> runasIds;
    @NotNull
    private Integer forjadorId;
    private String nombreForjador;

}
