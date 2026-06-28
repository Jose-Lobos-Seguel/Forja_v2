package com.mlg.forja.DTO;

import java.util.List;

import com.forja.equipamiento.DTO.EnanoDTO;

import lombok.Data;

@Data
public class RegionDTO {
    private Integer id;
    private String nombre;
    private String descripcion;

    //Clases Relacionadas
    private List <ClanDTO> clan;
    private List <EnanoDTO> enano;
}
