package com.mlg.forja.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"id", "nombre", "descripcion", "materialesIds", "purezas"})
public class DimensionDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private List<Integer> materialesIds;
    private List<Integer> purezas;
}
