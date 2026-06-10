package com.mlg.forja.controller;

import org.springframework.web.bind.annotation.RestController;
import com.mlg.forja.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mlg.forja.modelo.Tipo;
import com.mlg.forja.DTO.TipoDTO;
import com.mlg.forja.modelo.Equipamiento;

@RestController
@RequestMapping("/forja/api/v1/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping
    public List<TipoDTO> obtenerTodos() {
        return tipoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public TipoDTO buscarPorId(@PathVariable Integer id) {
        return tipoService.buscarPorId(id);
    }

    @PostMapping
    public TipoDTO guardarTipo(@RequestBody TipoDTO tipoDTO) {
        return tipoService.guardarTipo(tipoDTO);
    }

    @PutMapping("/{id}")
    public TipoDTO actualizarTipo(@PathVariable Integer id, @RequestBody TipoDTO tipoDTO) {
        return tipoService.actualizarTipo(id, tipoDTO);
    }

    @GetMapping("/buscar")
    public List<Tipo> buscarPorNombre(@RequestParam String nombre) {
        return tipoService.buscarPorNombre(nombre);
    }

    @GetMapping("/equipamiento/{equipamiento_id}")
    public List<Tipo> buscarPorEquipamiento(@PathVariable Integer equipamiento_id) {
        Equipamiento equipamiento = new Equipamiento();
        equipamiento.setId(equipamiento_id);
        return tipoService.buscarPorEquipamiento(equipamiento);
    }

    @DeleteMapping("/{id}")
    public String eliminarTipo(@PathVariable Integer id) {
        return tipoService.eliminarTipo(id);
    }
}
