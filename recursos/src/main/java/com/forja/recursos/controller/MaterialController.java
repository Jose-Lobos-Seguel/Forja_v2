package com.forja.recursos.controller;

import java.util.List;

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

import com.forja.recursos.DTO.MaterialDTO;
import com.forja.recursos.service.MaterialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/materiales")
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    public ResponseEntity<List<MaterialDTO>> obtenerTodos() {
        List<MaterialDTO> materiales = materialService.listar();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(materiales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            MaterialDTO material = materialService.buscarPorId(id);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(material);
        } 
        catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<MaterialDTO> guardar(@RequestBody MaterialDTO dto) {
        MaterialDTO nuevoMaterial = materialService.guardar(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(nuevoMaterial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,@RequestBody MaterialDTO dto) 
    {
        try 
        {
            MaterialDTO actualizado = materialService.actualizar(id, dto);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(actualizado);

        }
        catch (RuntimeException e)
        {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMaterial(@PathVariable Integer id) {
        try {
            String resultado = materialService.eliminar(id);
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
