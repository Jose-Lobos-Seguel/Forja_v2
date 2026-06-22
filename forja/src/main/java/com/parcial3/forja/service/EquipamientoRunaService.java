package com.parcial3.forja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial3.forja.model.EquipamientoRunaEntidad;
import com.parcial3.forja.repository.EquipamientoRunaRepository;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class EquipamientoRunaService {
    @Autowired
    private EquipamientoRunaRepository equipamientoRunaRepository;

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
