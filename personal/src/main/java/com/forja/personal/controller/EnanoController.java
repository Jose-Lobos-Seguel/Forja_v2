package com.mlg.forja.controller;

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

import com.mlg.forja.DTO.EnanoDTO;
import com.mlg.forja.service.EnanoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/enanos")
public class EnanoController {

    private final EnanoService enanoService;

    @GetMapping
    public ResponseEntity<List<EnanoDTO>> obtenerTodos() 
    {
        List<EnanoDTO> enanos = enanoService.listar();

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(enanos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) 
    {
        try 
        {
            EnanoDTO enano = enanoService.buscarPorId(id);

            return ResponseEntity.ok(enano);
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<EnanoDTO> guardarEnano(@RequestBody EnanoDTO enano) 
    {

        EnanoDTO nuevoEnano = enanoService.guardar(enano);

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevoEnano);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,@RequestBody EnanoDTO dto) 
    {
        try 
        {
            EnanoDTO actualizado = enanoService.actualizar(id, dto);
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
    public ResponseEntity<String> eliminarEnano(@PathVariable Integer id) 
    {
        String resultado =
                enanoService.eliminarEnano(id);

        return ResponseEntity.ok(resultado);
    }
}
