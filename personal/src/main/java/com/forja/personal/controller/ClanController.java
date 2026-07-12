package com.forja.personal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forja.personal.DTO.ClanDTO;
import com.forja.personal.service.ClanService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/clanes")
public class ClanController {

    private final ClanService clanService;

    @GetMapping
    public ResponseEntity<List<ClanDTO>> obtenerTodos() 
    {
        List<ClanDTO> clanes = clanService.listar();

        return ResponseEntity.ok(clanes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) 
    {
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

    @PostMapping
    public ResponseEntity<ClanDTO> guardarClan(@RequestBody ClanDTO clan) 
    {
        ClanDTO nuevoClan = clanService.guardar(clan);

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevoClan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClan(@PathVariable Integer id) 
    {
        String resultado = clanService.eliminarClan(id);

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(resultado);
    }
}
