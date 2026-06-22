package com.parcial3.mundo.DTO;

import java.util.List;

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
