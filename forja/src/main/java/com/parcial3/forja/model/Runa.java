package com.parcial3.forja.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "runa")
public class Runa 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identificador de la runa
    private Integer id;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "El nombre debe contener entre 2 y 50 caracteres")
    @NotBlank
    @NotNull
    //Nombre de la runa
    private String nombre;

    @Column(nullable = true, length = 50)
    @Size(min = 2, max = 50, message = "El elemento debe contener entre 2 y 50 caracteres")
    @NotBlank
    @NotNull
    //Elemento de la runa
    //Puede ser NULL
    //NULL indica que la runa no tiene elemento
    private String elemento;

    @OneToMany(mappedBy = "runa")
    private List<EquipamientoRunaEntidad> equipamientos;
}
