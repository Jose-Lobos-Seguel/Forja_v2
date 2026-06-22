package com.mlg.forja.modelo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name = "equipamiento")
public class Equipamiento 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identificador del arma
    private Integer id;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "El nombre debe contener entre 2 y 50 caracteres")
    @NotBlank
    @NotNull
    //Nombre del arma
    private String nombre;

    @Column(nullable = false, length = 25)
    @Size(min = 2, max = 25, message = "La calidad debe contener entre 4 y 25 caracteres")
    @NotBlank
    @NotNull
    //Calidad del arma
    private String calidad;

    @Column(nullable = false)
    @Min(0)
    @Max(5)
    @NotNull
    //Indica la cantidad de runas que puede tener un equipamiento
    //esto servira en la logica para controlar las runas en el equipamiento
    private Integer maxRunas;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    //Esta es la relacion entre el equipamiento y su tipo
    //cada equipamiento puede tener solo un tipo
    //a su vez un tipo se puede compartir entre muchos equipamientos
    private Tipo tipo;

    @OneToMany(mappedBy = "equipamiento")
    //Indica los materiales del cual esta compuesto el equipamiento
    private List<MaterialDimension> materiales;

    @OneToMany(mappedBy = "equipamiento")
    //Indica las runas que estan inbuidas en el equipamiento
    private List<EquipamientoRunaEntidad> runas;

    @ManyToOne
    @JoinColumn(name = "forjador_id")
    //Indica el enano que forjo esta u otro grupo o cantidad de armas
    private Enano forjador;
}
