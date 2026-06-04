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
@Table(name = "enano")
public class Enano 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identificador del enano
    private Integer id;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "El nombre debe contener entre 2 y 50 caracteres")
    @NotBlank
    @NotNull
    //Nombre del enano
    private String nombre;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "El nombre debe contener entre 2 y 50 caracteres")
    @NotBlank
    @NotNull
    //Apellido del enano
    private String apellido;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "El nombre debe contener entre 2 y 50 caracteres")
    @NotBlank
    @NotNull
    //Titulo del enano
    private String titulo;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "El nombre debe contener entre 2 y 50 caracteres")
    @NotBlank
    @NotNull
    //Especialidad del enano
    //podemos utilizarla para medir la calidad del equipamiento despues
    private String especialidad;

    @ManyToOne
    @JoinColumn(name = "clan_id")
    //Relaciona a los enanos en un clan
    private Clan clan;

    @OneToMany(mappedBy = "forjador")
    // Lista de equipamientos forjados por el enano
    private List<Equipamiento> equipamientosForjados;
}
