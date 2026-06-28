package com.forja.equipamiento.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forja.equipamiento.modelo.EquipamientoRunaEntidad;
import com.forja.equipamiento.repository.EquipamientoRunaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@Transactional
@RequiredArgsConstructor
public class EquipamientoRunaService {

    private final EquipamientoRunaRepository equipamientoRunaRepository;

    public List<EquipamientoRunaEntidad> obtenerTodasLasRelaciones() {
        return equipamientoRunaRepository.findAll();
    }

    public String eliminarRunaDeEquipo(Integer relacionId) {
        EquipamientoRunaEntidad er = equipamientoRunaRepository.findById(relacionId)
                .orElseThrow(() -> new RuntimeException("La runa no está engarzada en este equipo."));
        
        String nombreRuna = er.getRuna().getNombre();
        equipamientoRunaRepository.delete(er);
        
        return "La runa '" + nombreRuna + "' ha sido extraída su equipamiento.";
    }
}
