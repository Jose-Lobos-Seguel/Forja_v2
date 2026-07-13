package com.forja.equipamiento.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forja.equipamiento.modelo.EquipamientoRunaEntidad;
import com.forja.equipamiento.service.EquipamientoRunaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/equipamientos-runas")
public class EquipamientoRunaController {

    private final EquipamientoRunaService equipamientoRunaService;

    @GetMapping
    public ResponseEntity<List<EquipamientoRunaEntidad>>
    obtenerTodasLasRelaciones() {

        List<EquipamientoRunaEntidad> relaciones =
                equipamientoRunaService.obtenerTodasLasRelaciones();

        return ResponseEntity.ok(relaciones);
    }

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