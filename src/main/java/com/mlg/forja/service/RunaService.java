package com.mlg.forja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.EquipamientoRunaDTO;
import com.mlg.forja.DTO.RunaDTO;
import com.mlg.forja.modelo.Runa;
import com.mlg.forja.modelo.EquipamientoRunaEntidad;
import com.mlg.forja.repository.RunaRepository;
import com.mlg.forja.repository.EquipamientoRunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RunaService 
{
    @Autowired
    private RunaRepository runaRepository;

    @Autowired
    private EquipamientoRunaRepository equipamientoRunaRepository;

    public List<RunaDTO> obtenerTodas() 
    {
        return runaRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

    public RunaDTO buscarPorId(Integer id) {
        Runa runa = runaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Runa no encontrada!"));
        return convertirADTO(runa);
    }

    public RunaDTO actualizar(Integer id, RunaDTO runaDTO) 
    {

    Runa runa = runaRepository.findById(id)
        .orElseThrow(() ->
            new RuntimeException("No existe la runa con ID: " + id));
                    
    if(runaDTO.getNombre() != null) {
        runa.setNombre(runaDTO.getNombre());
    }
    if(runaDTO.getElemento() != null) {
        runa.setElemento(runaDTO.getElemento());
    }
    
    // Vincular EquipamientoRunaEntidades si se proporcionan
    if(runaDTO.getEquipamientosIds() != null && !runaDTO.getEquipamientosIds().isEmpty()) {
        List<EquipamientoRunaEntidad> equipamientos = equipamientoRunaRepository.findAllById(runaDTO.getEquipamientosIds());
        for(EquipamientoRunaEntidad equipRuna : equipamientos) {
            equipRuna.setRuna(runa);
            equipamientoRunaRepository.save(equipRuna);
        }
    }

    runaRepository.save(runa);
    return convertirADTO(runa);

    }

    public RunaDTO guardar(RunaDTO runaDTO) 
    {
        Runa runa = convertirEntidad(runaDTO);
        runaRepository.save(runa);
        
        // Vincular EquipamientoRunaEntidades si se proporcionan
        if(runaDTO.getEquipamientosIds() != null && !runaDTO.getEquipamientosIds().isEmpty()) {
            List<EquipamientoRunaEntidad> equipamientos = equipamientoRunaRepository.findAllById(runaDTO.getEquipamientosIds());
            for(EquipamientoRunaEntidad equipRuna : equipamientos) {
                equipRuna.setRuna(runa);
                equipamientoRunaRepository.save(equipRuna);
            }
        }
        
        return runaDTO;
    }

    public String eliminar(Integer id) {
        Runa runa = runaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la runa con ID: " + id));
        runaRepository.delete(runa);
        return "La runa '" + runa.getNombre() + "' ha sido destruida.";
    }

    private Runa convertirEntidad(RunaDTO dto) {
        Runa runa = new Runa();
        runa.setId(dto.getId());
        runa.setNombre(dto.getNombre());
        runa.setElemento(dto.getElemento());
        return runa;
    }

    private RunaDTO convertirADTO(Runa runa) 
    {
        RunaDTO dto = new RunaDTO();
        dto.setId(runa.getId());
        dto.setNombre(runa.getNombre());
        dto.setElemento(runa.getElemento());
        
        if (runa.getEquipamientos() != null) 
        {
            List<EquipamientoRunaDTO> equipamientoDTO = runa.getEquipamientos().stream()
                .map(equip -> 
                {
                    EquipamientoRunaDTO dtoEquip = new EquipamientoRunaDTO();
                    return dtoEquip;
                })
                .toList();
            dto.setEquipamiento(equipamientoDTO);
        }
        return dto;
    }
}
