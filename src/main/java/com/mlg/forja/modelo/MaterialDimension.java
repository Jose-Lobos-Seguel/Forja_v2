package com.mlg.forja.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "material_dimension")
public class MaterialDimension 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identificador del clan
    private Integer id;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    @NotNull
    //Indica la pureza o calidad del material
    //se utilizara en la logica para calcular la calidad del arma
    private Integer pureza;

    @ManyToOne
    @JoinColumn(name = "equipamiento_id")
    //Relaciona material con equipamiento por equipamiento_id
    //indica que un equipamiento x esta compuesto por x,y,z materiales
    //la lista para almacenar los materiales se instancia en la clase equipamiento
    private Equipamiento equipamiento;

    @ManyToOne
    @JoinColumn(name = "material_id")
    @ToString.Exclude
    //Relacion entre el material y esta clase
    //Junto a la relacion "dimension_id" convierten a esta clase en tabla intermedia
    private Material material;

    @ManyToOne
    @JoinColumn(name = "dimension_id")
    @ToString.Exclude
    //Relacion entre la dimension y esta clase
    //Junto a la relacion "material_id" convierten a esta clase en tabla intermedia
    private Dimension dimension;
}
