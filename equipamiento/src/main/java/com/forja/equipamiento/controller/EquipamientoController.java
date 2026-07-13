package com.forja.equipamiento.controller;

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

import com.forja.equipamiento.DTO.EquipamientoDTO;
import com.forja.equipamiento.service.EquipamientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/equipamientos")
public class EquipamientoController {

    private final EquipamientoService equipamientoService;

    @GetMapping
    public ResponseEntity<List<EquipamientoDTO>> obtenerTodos() 
    {
        List<EquipamientoDTO> equipamientos = equipamientoService.listar();

        return ResponseEntity.ok(equipamientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) 
    {
        try 
        {
            EquipamientoDTO equipamiento = equipamientoService.buscarPorId(id);
            return ResponseEntity.ok(equipamiento);

        }
        catch (RuntimeException e) 
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/buscar-por-enano/{id}")
    public ResponseEntity<?> buscarPorIdDeEnano(@PathVariable Integer id) 
    {
        try 
        {
            List<EquipamientoDTO> equipamiento = equipamientoService.buscarPorIdDeEnano(id);
            return ResponseEntity.ok(equipamiento);

        }
        catch (RuntimeException e) 
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<EquipamientoDTO> guardar(@RequestBody EquipamientoDTO dto) {
        EquipamientoDTO nuevoEquipamiento = equipamientoService.guardar(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(nuevoEquipamiento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) 
    {

        String resultado = equipamientoService.eliminar(id);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,@Valid @RequestBody EquipamientoDTO equipamientoActualizado) 
    {
        try 
        {
            EquipamientoDTO actualizado = equipamientoService.actualizar(id,equipamientoActualizado);
            return ResponseEntity.ok(actualizado);

        }
        catch (RuntimeException e)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}