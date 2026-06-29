package com.forja.equipamiento.DTO;

import lombok.Data;

@Data
public class MaterialDimensionDTORemoto {
    private Integer id;
    
    private Integer pureza;
    
    private Integer materialId;
    private String materialNombre;
    
    private Integer dimensionId;
    private String dimensionNombre;
}
