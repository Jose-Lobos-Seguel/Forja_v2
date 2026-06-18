package com.mlg.forja.DTO;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialDTO {
    @NotNull
    private Integer id;
    private String nombre;
    private List<Integer> dimensionesIds;
    private List<Integer> purezas;
}
