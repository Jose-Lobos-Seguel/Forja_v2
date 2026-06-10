package com.mlg.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ClanDTO {
    private Integer id;
    private String nombre;
    //Relacion con otras clases
    private List<Integer> enanosIds;
}
