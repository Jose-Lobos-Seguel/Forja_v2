package com.parcial3.mundo.DTO;

import java.util.List;

import lombok.Data;

@Data
public class DimensionDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    // Lista de nombres o IDs de materiales para evitar cargar objetos pesados
    private List<String> nombresMateriales;
}
