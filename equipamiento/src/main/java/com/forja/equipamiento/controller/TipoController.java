package com.mlg.forja.controller;

import org.springframework.web.bind.annotation.RestController;
import com.mlg.forja.service.TipoService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mlg.forja.DTO.TipoDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forja/api/v1/tipos")
public class TipoController {

    private final TipoService tipoService;

    @GetMapping
    public List<TipoDTO> obtenerTodos() {
        return tipoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public TipoDTO buscarPorId(@PathVariable Integer id) {
        return tipoService.buscarPorId(id);
    }

    @PostMapping
    public TipoDTO guardarTipo(@RequestBody TipoDTO tipo) {
        return tipoService.guardar(tipo);
    }

    @DeleteMapping("/{id}")
    public String eliminarTipo(@PathVariable Integer id) {
        return tipoService.eliminarTipo(id);
    }   
}
