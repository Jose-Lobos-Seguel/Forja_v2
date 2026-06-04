package com.mlg.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class MaterialDTO {
    private Integer id;
    private String nombre;
    private Integer equipamientoId; // Solo el ID para no arrastrar todo el objeto Equipamiento
    private String nombreEquipamiento;
    private List<String> nombresDimensiones;
}
