package com.mlg.forja.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"id", "nombre", "dimensionesIds", "purezas"})
public class MaterialDTO {
    private Integer id;
    private String nombre;
    private List<Integer> dimensionesIds;
    private List<Integer> purezas;
}
