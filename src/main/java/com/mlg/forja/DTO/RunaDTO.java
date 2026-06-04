package com.mlg.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class RunaDTO 
{
    private Integer id;
    private String nombre;
    private String elemento;
    private List<EquipamientoRunaDTO> equipamiento;
}
