package com.mlg.forja.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "equipamiento_runa")
public class EquipamientoRunaEntidad 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identificador del clan
    private Integer id;

    @Column(nullable = true, length = 25)
    @Size(min = 3, max = 25, message = "El elemento debe contener entre 3 y 50 caracteres")
    @NotBlank
    //Bonus de la runa
    //Puede ser NULL
    //NULL indica que la runa no añade un bonus
    private String bonus;

    @ManyToOne
    @JoinColumn(name = "runa_id")
    //Relacion entre el runa y esta clase
    //Junto a la relacion "equipamiento_id" convierten a esta clase en tabla intermedia
    private Runa runa;

    @ManyToOne
    @JoinColumn(name = "equipamiento_id")
    //Relacion entre el equipamiento y esta clase
    //Junto a la relacion "runa_id" convierten a esta clase en tabla intermedia
    private Equipamiento equipamiento;
}
