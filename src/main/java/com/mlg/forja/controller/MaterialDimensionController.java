package com.mlg.forja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlg.forja.DTO.MaterialDimensionDTO;
import com.mlg.forja.service.MaterialDimensionService;

@RestController
@RequestMapping("/forja/api/v1/material-dimension")
public class MaterialDimensionController {
    @Autowired
    private MaterialDimensionService materialDimensionService;

    @GetMapping
    public ResponseEntity<List<MaterialDimensionDTO>> listarRelaciones() {
        List<MaterialDimensionDTO> relaciones = materialDimensionService.obtenerRelaciones();
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDimensionDTO> buscarPorId(@PathVariable Integer id) {
        try {
            MaterialDimensionDTO dto = materialDimensionService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para actualizar la pureza de un material en una dimensión específica
    @PutMapping("/{id}/pureza")
    public ResponseEntity<MaterialDimensionDTO> actualizarPureza(@PathVariable Integer id, @RequestBody Integer nuevaPureza) {
        try {
            MaterialDimensionDTO actualizado = materialDimensionService.actualizarPureza(id, nuevaPureza);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody MaterialDimensionDTO materialDimensionDTO) {
        try {
            MaterialDimensionDTO actualizado = materialDimensionService.actualizar(id, materialDimensionDTO);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRelacion(@PathVariable Integer id) {
        try {
            String resultado = materialDimensionService.eliminarRelacion(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
