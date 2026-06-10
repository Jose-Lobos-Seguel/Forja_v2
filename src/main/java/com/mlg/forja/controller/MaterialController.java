package com.mlg.forja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlg.forja.DTO.MaterialDTO;
import com.mlg.forja.service.MaterialService;

@RestController
@RequestMapping("/forja/api/v1/materiales")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping
    public ResponseEntity<List<MaterialDTO>> obtenerTodas() {
        List<MaterialDTO> materials = materialService.obtenerTodos();
        if (materials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> buscarPorId(@PathVariable Integer id) {
        try {
            MaterialDTO material = materialService.buscarPorId(id);
            return new ResponseEntity<>(material, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<MaterialDTO> crearMaterial(@RequestBody MaterialDTO materialDTO) {
        MaterialDTO materialGuardado = materialService.guardar(materialDTO);
        return new ResponseEntity<>(materialGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> actualizarMaterial(@PathVariable Integer id, @RequestBody MaterialDTO materialDTO) {
        try {
            MaterialDTO actualizado = materialService.actualizarMaterial(id, materialDTO);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{materialId}/dimension/{dimensionId}")
    public ResponseEntity<String> asignarDimension(@PathVariable Integer materialId, @PathVariable Integer dimensionId) {
        try {
            String resultado = materialService.añadirDimensionAMaterial(materialId, dimensionId);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{materialId}/dimension/{dimensionId}")
    public ResponseEntity<String> desasignarDimension(@PathVariable Integer materialId, @PathVariable Integer dimensionId) {
        try {
            String resultado = materialService.desasignarMaterialDeDimension(materialId, dimensionId);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMaterial(@PathVariable Integer id) {
        try {
            String resultado = materialService.eliminarMaterial(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
