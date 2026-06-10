package com.mlg.forja.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mlg.forja.modelo.Tipo;
import com.mlg.forja.DTO.TipoDTO;
import com.mlg.forja.modelo.Equipamiento;
import com.mlg.forja.repository.TipoRepository;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class TipoService {
    @Autowired
    private TipoRepository tipoRepository;

    public List<TipoDTO> obtenerTodos()
    {
        return tipoRepository.findAll().stream()
            .map(this::convertirDTO)
            .toList();
    }

    public TipoDTO buscarPorId(Integer id) {
       Tipo tipo = tipoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El tipo de equipamiento que esta buscando no ha sido forjado aun"));
        return convertirDTO(tipo);
    }

    public String eliminarTipo(Integer id) {
        try {
           Tipo tipo = tipoRepository.findById(id)
                   .orElseThrow(() -> new RuntimeException("Seria imposible borrar de la existencia al tipo de equipamiento con ID de " + id + " pues estos no existen."));
           tipoRepository.delete(tipo);
           return "El tipo '" + tipo.getNombre() + "' ha sido eliminado de esta existencia exitosamente.";
       } catch (RuntimeException e) {
           return e.getMessage();
        }
    }

    public TipoDTO guardarTipo(TipoDTO tipoDTO) {
        Tipo tipo = new Tipo();
        tipo.setNombre(tipoDTO.getNombre());
        return convertirDTO(tipoRepository.save(tipo));
    }

    public TipoDTO actualizarTipo(Integer id, TipoDTO tipoDTO) {
        Tipo tipo = tipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el tipo con ID: " + id));

        if (tipoDTO.getNombre() != null) {
            tipo.setNombre(tipoDTO.getNombre());
        }
        return convertirDTO(tipoRepository.save(tipo));
    }

    public List<Tipo> buscarPorNombre(String nombre){
       return tipoRepository.findByNombre(nombre);
    }

    public List<Tipo> buscarPorEquipamiento(Equipamiento equipamiento_id){
        return buscarPorEquipamiento(equipamiento_id);
    }

    public TipoDTO convertirDTO(Tipo tipo)
    {
        TipoDTO dto = new TipoDTO();

        dto.setId(tipo.getId());
        dto.setNombre(tipo.getNombre());

        if (tipo.getEquipamientos() != null && !tipo.getEquipamientos().isEmpty()) 
        {

            List<Integer> tiposIds = tipo.getEquipamientos().stream()
                .map(Equipamiento::getId)
                .toList();

            dto.setEquipamientoIds(tiposIds);
        }
        return dto;
    }
}
