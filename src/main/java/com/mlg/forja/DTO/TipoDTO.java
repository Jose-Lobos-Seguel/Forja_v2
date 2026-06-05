package com.mlg.forja.DTO;

import java.util.List;

import lombok.Data;

@Data
public class TipoDTO 
{
    private Integer id;
    private String nombre;
    public void setEquipamientoIds(List<Integer> tiposIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEquipamientoIds'");
    }
}
