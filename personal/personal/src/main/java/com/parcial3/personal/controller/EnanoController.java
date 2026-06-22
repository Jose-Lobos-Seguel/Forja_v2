package com.parcial3.personal.controller;

import java.util.List;

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

import com.parcial3.personal.DTO.EnanoDTO;
import com.parcial3.personal.model.Enano;
import com.parcial3.personal.model.Equipamiento;
import com.parcial3.personal.service.EnanoService;

@RestController
@RequestMapping("/forja/api/v1/enanos")
public class EnanoController {

    @Autowired
    private EnanoService enanoService;

    @GetMapping
    public ResponseEntity<List<EnanoDTO>> obtenerTodos() {
        List<EnanoDTO> enanos = enanoService.obtenerTodos();

        return ResponseEntity.ok(enanos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
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
    public ResponseEntity<Enano> guardarEnano(@RequestBody Enano enano) {

        Enano nuevoEnano =
                enanoService.guardarEnano(enano);

        return new ResponseEntity<>(
                nuevoEnano,
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEnano(@PathVariable Integer id) {
        String resultado =
                enanoService.eliminarEnano(id);

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<EnanoDTO>> buscarPorNombre(@PathVariable String nombre) 
    {

        List<EnanoDTO> enanos =
                enanoService.buscarPorNombre(nombre);

        return ResponseEntity.ok(enanos);
    }

    @GetMapping("/clan/{clanId}")
    public ResponseEntity<?> buscarPorClan(@PathVariable Integer clanId) {
        try 
        {
            List<EnanoDTO> enanos = enanoService.buscarPorClan(clanId);

            return ResponseEntity.ok(enanos);
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/{enanoId}/equipamientos")
    public ResponseEntity<?> obtenerEquipamientosForjados(@PathVariable Integer enanoId) {
        try
        {
            List<Equipamiento> equipamientos =
                enanoService.obtenerEquipamientosForjados(enanoId);

            return ResponseEntity.ok(equipamientos);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity
                .badRequest()
                .body(e.getMessage());
        }
    }
}
