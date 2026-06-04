package com.mlg.forja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlg.forja.modelo.EquipamientoRunaEntidad;
import com.mlg.forja.service.EquipamientoRunaService;

@RestController
@RequestMapping("/forja/api/v1/equipamientos-runas")
public class EquipamientoRunaController {

    @Autowired
    private EquipamientoRunaService equipamientoRunaService;

    // Obtener todas las relaciones
    @GetMapping
    public ResponseEntity<List<EquipamientoRunaEntidad>>
    obtenerTodasLasRelaciones() {

        List<EquipamientoRunaEntidad> relaciones =
                equipamientoRunaService.obtenerTodasLasRelaciones();

        return ResponseEntity.ok(relaciones);
    }

    // Eliminar runa de un equipamiento
    @DeleteMapping("/{relacionId}")
    public ResponseEntity<String> eliminarRunaDeEquipo(
            @PathVariable Integer relacionId) {

        try {

            String resultado =
                    equipamientoRunaService
                            .eliminarRunaDeEquipo(relacionId);

            return ResponseEntity.ok(resultado);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}