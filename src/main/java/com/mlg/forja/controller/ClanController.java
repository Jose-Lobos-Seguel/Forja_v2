package com.mlg.forja.controller;

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

import com.mlg.forja.DTO.ClanDTO;
import com.mlg.forja.DTO.EnanoDTO;
import com.mlg.forja.service.ClanService;

import java.util.List;

@RestController
@RequestMapping("/forja/api/v1/clanes")
public class ClanController {

    @Autowired
    private ClanService clanService;

    @GetMapping
    public ResponseEntity<List<ClanDTO>> obtenerTodos() 
    {
        List<ClanDTO> clanes = clanService.obtenerTodos();

        return ResponseEntity.ok(clanes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try 
        {
            ClanDTO clan = clanService.buscarPorId(id);
            return ResponseEntity.ok(clan);
        }
        catch (RuntimeException e) 
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClanDTO>> buscarPorNombre(@PathVariable String nombre) 
    {
        List<ClanDTO> clanes = clanService.buscarPorNombre(nombre);

        return ResponseEntity.ok(clanes);
    }

    @GetMapping("/{id}/miembros")
    public ResponseEntity<List<EnanoDTO>> buscarMiembros(@PathVariable Integer id) 
    {
        List<EnanoDTO> miembros = clanService.buscarMiembros(id);

        return ResponseEntity.ok(miembros);
    }

    @PostMapping
    public ResponseEntity<ClanDTO> guardarClan(@RequestBody ClanDTO clanDTO) 
    {
        ClanDTO nuevoClan = clanService.guardarClan(clanDTO);
        return new ResponseEntity<>(nuevoClan, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClanDTO> actualizarClan(@PathVariable Integer id, @RequestBody ClanDTO clanDTO) {
        try {
            ClanDTO actualizado = clanService.actualizarClan(id, clanDTO);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClan(@PathVariable Integer id) 
    {
        String resultado = clanService.eliminarClan(id);

        return ResponseEntity.ok(resultado);
    }
}