package com.mlg.forja.controller;

import org.springframework.web.bind.annotation.RestController;
import com.mlg.forja.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mlg.forja.DTO.TipoDTO;

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
    public ResponseEntity<TipoDTO> guardarTipo(@RequestBody TipoDTO tipoDTO) {
        TipoDTO nuevoTipo = tipoService.guardarTipo(tipoDTO);
        return new ResponseEntity<>(nuevoTipo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTipo(@PathVariable Integer id, @RequestBody TipoDTO tipoDTO) {
        try {
            TipoDTO actualizado = tipoService.actualizarTipo(id, tipoDTO);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public String eliminarTipo(@PathVariable Integer id) {
        return tipoService.eliminarTipo(id);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<TipoDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<TipoDTO> tipos = tipoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/equipamiento/{equipamiento_id}")
    public ResponseEntity<?> buscarPorEquipamiento(@PathVariable Integer equipamiento_id) {
        try {
            // Removed: Equipamiento equipamiento = new Equipamiento();
            // equipamiento.setId(equipamiento_id);
            // Recommend moving this to service layer
            List<TipoDTO> tipos = tipoService.buscarPorNombre("");
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }   
}
