package com.mlg.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class RunaDTO 
{
    private Integer id;
    private String nombre;
    private String elemento;
    // IDs de EquipamientoRunaEntidad para vincular equipamientos
    private List<Integer> equipamientosIds;
    // Lista de detalles de equipamientos (para lectura/display)
    private List<EquipamientoRunaDTO> equipamiento;
}
