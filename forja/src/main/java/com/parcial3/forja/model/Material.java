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
@Table(name = "material")
public class Material 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identificador del material
    private Integer id;

    @Column(nullable = false, length = 100)
    @Size(min = 2, max = 100, message = "El nombre debe contener entre 2 y 100 caracteres")
    @NotBlank
    @NotNull
    //Nombre del material
    private String nombre;

    @OneToMany(mappedBy = "material")
    //Relacion con la tabla MaterialDimension
    //indica las dimensiones donde puede aparecer el material
    private List<MaterialDimension> dimensiones;
}
