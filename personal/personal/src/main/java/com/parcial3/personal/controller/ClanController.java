package com.parcial3.personal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial3.personal.DTO.ClanDTO;
import com.parcial3.personal.DTO.EnanoDTO;
import com.parcial3.personal.model.Clan;
import com.parcial3.personal.service.ClanService;

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
    public ResponseEntity<Clan> guardarClan(@RequestBody Clan clan) 
    {
        Clan nuevoClan = clanService.guardarClan(clan);

        return new ResponseEntity<>(nuevoClan, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClan(@PathVariable Integer id) 
    {
        String resultado = clanService.eliminarClan(id);

        return ResponseEntity.ok(resultado);
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
}
