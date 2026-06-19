package com.mlg.forja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlg.forja.DTO.EquipamientoRunaDTO;
import com.mlg.forja.service.EquipamientoRunaService;

@RestController
@RequestMapping("/forja/api/v1/equipamientos-runas")
public class EquipamientoRunaController {

    @Autowired
    private EquipamientoRunaService equipamientoRunaService;

    // Obtener todas las relaciones
    @GetMapping
    public ResponseEntity<List<EquipamientoRunaDTO>>
    obtenerTodasLasRelaciones() {

        List<EquipamientoRunaDTO> relaciones =
                equipamientoRunaService.obtenerTodasLasRelaciones();

        return ResponseEntity.ok(relaciones);
    }

    // Obtener relación por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            EquipamientoRunaDTO relacion = equipamientoRunaService.buscarPorId(id);
            return ResponseEntity.ok(relacion);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Actualizar relación
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody EquipamientoRunaDTO equipamientoRunaDTO) {
        try {
            EquipamientoRunaDTO actualizado = equipamientoRunaService.actualizar(id, equipamientoRunaDTO);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
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