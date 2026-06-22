package com.parcial3.mundo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "region")
public class Region 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identificador de la region
    private Integer id;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "El nombre debe contener entre 2 y 50 caracteres")
    @NotBlank
    @NotNull
    //Nombre de la region
    private String nombre;

    @Column(nullable = false, length = 150)
    @Size(min = 2, max = 150, message = "El nombre debe contener entre 2 y 150 caracteres")
    @NotBlank
    @NotNull
    //Descripcion de la region
    private String descripcion;
}
