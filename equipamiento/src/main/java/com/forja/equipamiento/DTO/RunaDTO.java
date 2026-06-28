package com.forja.equipamiento.DTO;

import java.util.List;

import lombok.Data;

@Data
public class RunaDTO 
{
    private Integer id;
    private String nombre;
    private String elemento;
    private String bonus;
    private List<Integer> equipamientosIds;
}
