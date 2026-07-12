package com.forja.recursos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forja.recursos.DTO.DimensionDTO;
import com.forja.recursos.service.DimensionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/dimensiones")
public class DimensionController {

    private final DimensionService dimensionService;

    @GetMapping
    public ResponseEntity<List<DimensionDTO>> obtenerTodas() {
        List<DimensionDTO> dimensiones = dimensionService.listar();
        if (dimensiones.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(dimensiones);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dimensiones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            DimensionDTO dimension = dimensionService.buscarPorId(id);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(dimension);
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<DimensionDTO> guardar(@RequestBody DimensionDTO dto) {
        DimensionDTO nuevaDimension = dimensionService.guardar(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(nuevaDimension);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDimension(@PathVariable Integer id, @RequestBody DimensionDTO dto) {
        try {
            DimensionDTO actualizada = dimensionService.actualizar(id, dto);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDimension(@PathVariable Integer id) {
        try {
            String resultado = dimensionService.eliminarDimension(id);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }
}
