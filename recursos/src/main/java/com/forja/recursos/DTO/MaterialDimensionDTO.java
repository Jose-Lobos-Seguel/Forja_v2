package com.forja.recursos.DTO;

import lombok.Data;

@Data
public class MaterialDimensionDTO {
    private Integer id; // Identificador de la relación
    
    private Integer pureza; // Nivel de calidad entre 1 y 5
    
    // ids y Nombres para evitar cargar toda la entidad y facilitar el uso en el Front-end
    private Integer materialId;
    private String materialNombre;
    
    private Integer dimensionId;
    private String dimensionNombre;
}
