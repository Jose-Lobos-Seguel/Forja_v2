package com.mlg.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class TipoDTO 
{
    private Integer id;
    private String nombre;
    
    //Relacion con otras clase
    private List<Integer> equipamientoIds;
}
