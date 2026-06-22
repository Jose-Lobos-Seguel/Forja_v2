package com.parcial3.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class MaterialDTO {
    private Integer id;
    private String nombre;
    private List<Integer> dimensionesIds;
    private List<Integer> purezas;
}
