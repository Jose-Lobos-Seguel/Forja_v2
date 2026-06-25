package com.mlg.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class DimensionDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    // Lista de IDs de MaterialDimension para vincular materiales a esta dimensión
    private List<Integer> materialDimensionIds;
    // Lista de nombres de materiales (para lectura/display)
    private List<String> nombresMateriales;
}
