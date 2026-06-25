package com.mlg.forja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.EquipamientoRunaDTO;
import com.mlg.forja.modelo.EquipamientoRunaEntidad;
import com.mlg.forja.repository.EquipamientoRunaRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class EquipamientoRunaService {
    @Autowired
    private EquipamientoRunaRepository equipamientoRunaRepository;

    public List<EquipamientoRunaDTO> obtenerTodasLasRelaciones() {
        return equipamientoRunaRepository.findAll()
            .stream()
            .map(this::convertirDTO)
            .toList();
    }

    public EquipamientoRunaDTO buscarPorId(Integer id) {
        EquipamientoRunaEntidad er = equipamientoRunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La relación no existe."));
        return convertirDTO(er);
    }

    public EquipamientoRunaDTO actualizar(Integer id, EquipamientoRunaDTO equipamientoRunaDTO) {
        EquipamientoRunaEntidad er = equipamientoRunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La relación no existe."));
        
        if(equipamientoRunaDTO.getBonus() != null) {
            er.setBonus(equipamientoRunaDTO.getBonus());
        }
        
        equipamientoRunaRepository.save(er);
        return convertirDTO(er);
    }

    public String eliminarRunaDeEquipo(Integer relacionId) {
        EquipamientoRunaEntidad er = equipamientoRunaRepository.findById(relacionId)
                .orElseThrow(() -> new RuntimeException("La runa no está engarzada en este equipo."));
        
        String nombreRuna = er.getRuna().getNombre();
        equipamientoRunaRepository.delete(er);
        
        return "La runa '" + nombreRuna + "' ha sido extraída su equipamiento.";
    }

    private EquipamientoRunaDTO convertirDTO(EquipamientoRunaEntidad entity) {
        EquipamientoRunaDTO dto = new EquipamientoRunaDTO();
        dto.setId(entity.getId());
        dto.setBonus(entity.getBonus());
        
        if(entity.getRuna() != null) {
            dto.setRunaId(entity.getRuna().getId());
            dto.setRunaNombre(entity.getRuna().getNombre());
            dto.setRunaElemento(entity.getRuna().getElemento());
        }
        
        return dto;
    }
}
