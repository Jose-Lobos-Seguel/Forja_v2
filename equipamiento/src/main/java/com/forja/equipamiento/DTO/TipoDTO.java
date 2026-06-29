package com.forja.equipamiento.DTO;

import java.util.List;

import lombok.Data;

@Data
public class TipoDTO 
{
    private Integer id;
    private String nombre;
    private List<Integer> equipamientos;
}
