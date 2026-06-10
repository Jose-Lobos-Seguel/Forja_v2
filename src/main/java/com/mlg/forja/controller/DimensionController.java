package com.mlg.forja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mlg.forja.DTO.DimensionDTO;

import com.mlg.forja.service.DimensionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/forja/api/v1/dimensiones")
public class DimensionController {
    @Autowired
    private DimensionService dimensionService;

    @GetMapping
    public ResponseEntity<List<DimensionDTO>> obtenerTodas() {
        List<DimensionDTO> dimensiones = dimensionService.obtenerTodas();
        if (dimensiones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dimensiones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DimensionDTO> buscarPorId(@PathVariable Integer id) {
        try {
            DimensionDTO dimension = dimensionService.buscarPorId(id);
            return new ResponseEntity<>(dimension, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DimensionDTO> crearDimension(@RequestBody DimensionDTO dimensionDTO) {
        try {
            DimensionDTO guardada = dimensionService.guardarDimension(dimensionDTO);
            return new ResponseEntity<>(guardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DimensionDTO> actualizarDimension(@PathVariable Integer id, @RequestBody DimensionDTO dimensionDTO) {
        try {
            DimensionDTO actualizada = dimensionService.actualizarDimension(id, dimensionDTO);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDimension(@PathVariable Integer id) {
        try {
            String resultado = dimensionService.eliminarDimension(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
