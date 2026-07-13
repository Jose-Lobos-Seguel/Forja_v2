package com.forja.recursos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forja.recursos.DTO.MaterialDimensionDTO;
import com.forja.recursos.modelo.MaterialDimension;
import com.forja.recursos.service.MaterialDimensionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/material-dimension")
public class MaterialDimensionController {

    private final MaterialDimensionService materialDimensionService;

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

    @GetMapping("/buscar-por-equipamiento/{id}")
    public ResponseEntity<?> buscarPorIdDeEquipamiento(@PathVariable Integer id) {
        try {
            List<MaterialDimensionDTO> dto = materialDimensionService.buscarPorIdDeEquipamiento(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar-lista-por-ids/{id}")
    public ResponseEntity<?> buscarPorIdDeEquipamiento(@PathVariable List<Integer> ids) {
        try {
            List<MaterialDimensionDTO> dto = materialDimensionService.buscarListaPorIdDeEquipamiento(ids);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/pureza")
    public ResponseEntity<MaterialDimension> actualizarPureza(@PathVariable Integer id, @RequestBody Integer nuevaPureza) {
        try {
            MaterialDimension actualizado = materialDimensionService.actualizarPureza(id, nuevaPureza);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/vincular-equipamiento")
    public ResponseEntity<Void> vincularMaterialesAEquipamiento(@Valid @RequestParam("equipamientoId") Integer equipamientoId, @RequestBody List<Integer> materialIds) {
        materialDimensionService.vincularEquipamientoAMateriales(equipamientoId, materialIds);
        return ResponseEntity.ok().build();
    }
}
