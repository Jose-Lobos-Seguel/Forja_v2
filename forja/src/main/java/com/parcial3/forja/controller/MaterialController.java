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
    public ResponseEntity<List<MaterialDTO>> obtenerTodos() 
    {
        List<MaterialDTO> materiales = materialService.listar();

        return ResponseEntity.ok(materiales);
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
            return ResponseEntity.ok(actualizado);

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
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
