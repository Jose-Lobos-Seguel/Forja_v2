package com.mlg.forja.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlg.forja.DTO.RegionDTO;
import com.mlg.forja.modelo.Region;
import com.mlg.forja.service.RegionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/region")
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDTO>> obtenerTodas() 
    {
        List<RegionDTO> regiones = regionService.obtenerTodos();

        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) 
    {
        try 
        {
            RegionDTO region = regionService.buscarPorId(id);
            return ResponseEntity.ok(region);

        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Region> guardarRegion(@RequestBody Region region) 
    {
        Region nuevaRegion = regionService.guardarRegion(region);
        return new ResponseEntity<>(
                nuevaRegion,
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRegion(@PathVariable Integer id) 
    {
        String resultado = regionService.eliminarRegion(id);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<RegionDTO>> buscarPorNombre( @PathVariable String nombre) 
    {
        List<RegionDTO> regiones =
                regionService.buscarPorNombre(nombre);

        return ResponseEntity.ok(regiones);
    }
}
