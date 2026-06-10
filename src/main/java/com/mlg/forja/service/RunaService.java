package com.mlg.forja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.RunaDTO;
import com.mlg.forja.modelo.Runa;
import com.mlg.forja.repository.RunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RunaService {
    @Autowired
    private RunaRepository runaRepository;

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

    public RunaDTO actualizar(Integer id, RunaDTO runaActualizada) 
    {
        Runa runa = runaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("No existe la runa con ID: " + id));

        if (runaActualizada.getNombre() != null) {
            runa.setNombre(runaActualizada.getNombre());
        }
        if (runaActualizada.getElemento() != null) {
            runa.setElemento(runaActualizada.getElemento());
        }

        return convertirADTO(runaRepository.save(runa));
    }

    public RunaDTO guardar(RunaDTO runaDTO) 
    {
        Runa runa = new Runa();
        runa.setNombre(runaDTO.getNombre());
        runa.setElemento(runaDTO.getElemento());
        return convertirADTO(runaRepository.save(runa));
    }

    public String eliminar(Integer id) {
        Runa runa = runaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la runa con ID: " + id));
        runaRepository.delete(runa);
        return "La runa '" + runa.getNombre() + "' ha sido destruida.";
    }

    private RunaDTO convertirADTO(Runa runa) 
    {
        RunaDTO dto = new RunaDTO();
        dto.setId(runa.getId());
        dto.setNombre(runa.getNombre());
        dto.setElemento(runa.getElemento());
        
        if (runa.getEquipamientos() != null) 
        {
            List<Integer> equipamientoIds = runa.getEquipamientos().stream()
                .map(equip -> equip.getId())
                .toList();
            dto.setEquipamientoIds(equipamientoIds);
        }
        return dto;
    }
}
