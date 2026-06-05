package com.mlg.forja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.EquipamientoRunaDTO;
import com.mlg.forja.DTO.RunaDTO;
import com.mlg.forja.modelo.Runa;
import com.mlg.forja.repository.RunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RunaService 
{
    @Autowired
    private RunaRepository runaRepository;

    public List<RunaDTO> obtenerTodas() 
    {
        return runaRepository.findAll().stream()
            .map(this::convertirDTO)
            .toList();
    }

    public RunaDTO buscarPorId(Integer id){
        Runa runa = runaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Runa no encontrada!"));
        return convertirDTO(runa);
    }

    public RunaDTO actualizar(Integer id, Runa runaActualizada){
    Runa runa = runaRepository.findById(id)
        .orElseThrow(() ->
            new RuntimeException("No existe la runa con ID: " + id));
                    
    runa.setNombre(runaActualizada.getNombre());
    runa.setElemento(runaActualizada.getElemento());

    runaRepository.save(runa);
    return convertirDTO(runa);
    }

    public Runa guardar(Runa runa){
        return runaRepository.save(runa);
    }
    public String eliminar(Integer id){
        Runa runa = runaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la runa con ID: " + id));
        runaRepository.delete(runa);
        return "La runa '" + runa.getNombre() + "' ha sido destruida.";
    }

    private RunaDTO convertirDTO(Runa runa){
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