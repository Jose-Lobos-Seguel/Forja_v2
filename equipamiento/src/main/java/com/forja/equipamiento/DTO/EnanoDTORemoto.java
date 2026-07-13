package com.forja.equipamiento.DTO;

import java.util.List;

import lombok.Data;

@Data
public class EnanoDTORemoto {
    private Integer id;
    private String nombre;
    private String apellido;
    private String titulo;

    //Relacion con otras clases
    private Integer clanId;
    private String clanNombre;
    private List<Integer> equipamientosForjadosIds; //Es mejor que este campo quede vacio al entrar como JSON principalmente se usa para mostrar los equipamientos que forjo el enano
}
