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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mlg.forja.DTO.EquipamientoDTO;
import com.mlg.forja.service.EquipamientoService;

@RestController
@RequestMapping("/forja/api/v1/equipamientos")
public class EquipamientoController {

    @Autowired
    private EquipamientoService equipamientoService;

    @GetMapping
    public ResponseEntity<List<EquipamientoDTO>> obtenerTodos() 
    {
        List<EquipamientoDTO> equipamientos = equipamientoService.obtenerTodos();

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

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody EquipamientoDTO equipamientoDTO)
    {
        try {
            EquipamientoDTO nuevoEquipamiento = equipamientoService.guardar(equipamientoDTO);
            return new ResponseEntity<>(nuevoEquipamiento,HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{equipamientoId}/runas/{runaId}")
    public ResponseEntity<String> colocarRuna(@PathVariable Integer equipamientoId,@PathVariable Integer runaId,@RequestParam String bonus)
    {
        try 
        {
            String resultado = equipamientoService.colocarRuna(equipamientoId,runaId,bonus);
            return ResponseEntity.ok(resultado);

        }
        catch (RuntimeException e)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,@RequestBody EquipamientoDTO equipamientoActualizado) 
    {
        try 
        {
            EquipamientoDTO actualizado = equipamientoService.updateEquipamiento(id,equipamientoActualizado);
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
    public ResponseEntity<String> eliminar(@PathVariable Integer id) 
    {

        String resultado = equipamientoService.eliminarMaterial(id);
        return ResponseEntity.ok(resultado);
    }
}
