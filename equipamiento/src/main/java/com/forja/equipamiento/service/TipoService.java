package com.forja.equipamiento.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.forja.equipamiento.DTO.TipoDTO;
import com.forja.equipamiento.modelo.Tipo;
import com.forja.equipamiento.repository.EquipamientoRepository;
import com.forja.equipamiento.repository.TipoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class TipoService {

    private final EquipamientoRepository equipamientoRepository;
    private final TipoRepository tipoRepository;

    public List<TipoDTO> obtenerTodos()
    {
        return tipoRepository.findAll().stream()
            .map(this::convertirDTO)
            .toList();
    }

    public TipoDTO buscarPorId(Integer id) {
       Tipo tipo = tipoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El tipo de equipamiento que se esta buscando no se contempla en esta forja"));
        return convertirDTO(tipo);
    }

    public String eliminarTipo(Integer id) {
        try {
           Tipo tipo = tipoRepository.findById(id)
                   .orElseThrow(() -> new RuntimeException("No se puede borrar un tipo de equipamiento que no existe"));
            if (tipo.getEquipamientos() != null && !tipo.getEquipamientos().isEmpty()) 
            {
                return "No puedes eliminar el tipo si aun hay equipamientos de este tipo";
            } 
            else 
            {
                tipoRepository.delete(tipo);
                return "El tipo '" + tipo.getNombre() + "' ha sido eliminado de esta existencia exitosamente.";
            }
       } catch (RuntimeException e) {
           return e.getMessage();
        }
    }

    public TipoDTO guardar(TipoDTO dto) {
        Tipo entidadTipo = convertirEntidad(dto);
        tipoRepository.save(entidadTipo);
       return convertirDTO(entidadTipo);
    }

    public TipoDTO convertirDTO(Tipo tipo) {
        TipoDTO dto = new TipoDTO();

        dto.setId(tipo.getId());
        dto.setNombre(tipo.getNombre());

        if (tipo.getEquipamientos() != null) 
        {
            List<Integer> equipamientosIds = tipo.getEquipamientos()
                .stream()
                .filter(Objects::nonNull)
                .map(equipamiento -> equipamiento.getId())
                .filter(Objects::nonNull)
                .toList();

            dto.setEquipamientos(equipamientosIds);
        }
        return dto;
    }

    public Tipo convertirEntidad(TipoDTO dto) {
        Tipo entidad = new Tipo();
        entidad.setNombre(dto.getNombre());

        if(dto.getEquipamientos() != null)
        {
            entidad.setEquipamientos(equipamientoRepository.findAllById(dto.getEquipamientos()));
        }

        return entidad;
    }
}
