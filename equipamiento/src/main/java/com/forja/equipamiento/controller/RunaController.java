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

import com.forja.equipamiento.DTO.RunaDTO;
import com.forja.equipamiento.service.RunaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/runas")
public class RunaController {

    private final RunaService runaService;

    @GetMapping
    public ResponseEntity<List<RunaDTO>> obtenerTodas() {

        List<RunaDTO> runas = runaService.listar();

        return ResponseEntity.ok(runas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) 
    {

        try 
        {
            RunaDTO runa = runaService.buscarPorId(id);

            return ResponseEntity.ok(runa);

        }
        catch (RuntimeException e) 
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<RunaDTO> guardar(@Valid @RequestBody RunaDTO runa) {

        RunaDTO nuevaRuna = runaService.guardar(runa);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(nuevaRuna);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,@Valid @RequestBody RunaDTO runaActualizada) {
        try {
            RunaDTO runa = runaService.actualizar(id,runaActualizada);

            return ResponseEntity.ok(runa);

        }
        catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {

        try {
            String resultado = runaService.eliminar(id);

            return ResponseEntity.ok(resultado);

        } 
        catch (RuntimeException e) {
            return ResponseEntity
            .status(HttpStatus.OK)
            .body(e.getMessage());
        }
    }
}
