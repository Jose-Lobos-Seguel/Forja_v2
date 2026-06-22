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

import com.mlg.forja.DTO.RunaDTO;
import com.mlg.forja.modelo.Runa;
import com.mlg.forja.service.RunaService;

@RestController
@RequestMapping("/forja/api/v1/runas")
public class RunaController {

    @Autowired
    private RunaService runaService;

    @GetMapping
    public ResponseEntity<List<RunaDTO>> obtenerTodas() {

        List<RunaDTO> runas = runaService.obtenerTodas();

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
    public ResponseEntity<Runa> guardar(
            @RequestBody Runa runa) {

        Runa nuevaRuna = runaService.guardar(runa);

        return new ResponseEntity<>(nuevaRuna,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,@RequestBody Runa runaActualizada) 
    {
        try 
        {
            Runa runa = runaService.actualizar(id,runaActualizada);

            return ResponseEntity.ok(runa);

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

        try 
        {
            String resultado = runaService.eliminar(id);

            return ResponseEntity.ok(resultado);

        } 
        catch (RuntimeException e) 
        {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
