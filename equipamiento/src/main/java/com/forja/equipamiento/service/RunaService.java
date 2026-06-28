package com.forja.equipamiento.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.forja.equipamiento.DTO.RunaDTO;
import com.forja.equipamiento.modelo.EquipamientoRunaEntidad;
import com.forja.equipamiento.modelo.Runa;
import com.forja.equipamiento.repository.EquipamientoRepository;
import com.forja.equipamiento.repository.EquipamientoRunaRepository;
import com.forja.equipamiento.repository.RunaRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RunaService {

    private final EquipamientoRepository equipamientoRepository;
    private final EquipamientoRunaRepository equipamientoRunaRepository;
    private final RunaRepository runaRepository;
    private final EntityManager entityManager;

    public List<RunaDTO> listar() 
    {
        return runaRepository.findAll().stream()
            .map(this::convertirDTO)
            .toList();
    }

    public RunaDTO buscarPorId(Integer id) {
        Runa runa = runaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Runa no encontrada!"));
        return convertirDTO(runa);
    }

    public RunaDTO guardar(RunaDTO dto) {
        Runa entidadRuna = convertirEntidad(dto);
        Runa runaGuardada = runaRepository.save(entidadRuna);
        List<EquipamientoRunaEntidad> relaciones = crearRelacion(runaGuardada, dto);
        equipamientoRunaRepository.saveAll(relaciones);
        return convertirDTO(runaGuardada);
    }

    public RunaDTO actualizar(Integer id, RunaDTO runaActualizada) {
        Runa runa = runaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar una runa con ese id"));
        
        if(runaActualizada.getNombre() != null)
        {
            runa.setNombre(runaActualizada.getNombre());
        }
        if(runaActualizada.getElemento() != null)
        {
            runa.setElemento(runaActualizada.getElemento());
        }
        if(runaActualizada.getBonus() != null)
        {
            runa.setBonus(runaActualizada.getBonus());
        }
        if(runaActualizada.getEquipamientosIds() != null)
        {
            runa.getEquipamientos().clear();
            runaRepository.flush();

            List<EquipamientoRunaEntidad> nuevasRelaciones = crearRelacion(runa, runaActualizada);
            runa.getEquipamientos().addAll(nuevasRelaciones);
        }
        Runa runaGuardada = runaRepository.saveAndFlush(runa);
        entityManager.refresh(runaGuardada);
        return convertirDTO(runaGuardada);
    }

    public String eliminar(Integer id) {
        try {
            Runa runa = runaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar la runa con ese id"));
            runaRepository.delete(runa);
            List<EquipamientoRunaEntidad> relaciones = equipamientoRunaRepository.findAllByRuna(runa);
            List<Integer> relacionesIds = relaciones.stream()
                                                    .filter(Objects::nonNull)
                                                    .map(equipamientoRuna -> equipamientoRuna.getId())
                                                    .filter(Objects::nonNull)
                                                    .toList();                
            equipamientoRunaRepository.deleteAllByIdInBatch(relacionesIds);

            return "La runa " + runa.getNombre() + " a sido eliminado junto a todas sus relaciones";
        }
        catch(RuntimeException e) {
            return e.getMessage();
        }
    }

    private RunaDTO convertirDTO(Runa runa) {
        RunaDTO dto = new RunaDTO();
        List<Integer> listaRelacionesIds = new ArrayList<>();

        dto.setId(runa.getId());
        dto.setNombre(runa.getNombre());
        dto.setElemento(runa.getElemento());
        dto.setBonus(runa.getBonus());

        for (EquipamientoRunaEntidad relacion : runa.getEquipamientos()) {
            listaRelacionesIds.add(relacion.getId());
        }
        dto.setEquipamientosIds(listaRelacionesIds);
        return dto;
    }

    public Runa convertirEntidad(RunaDTO dto) {
        Runa entidad = new Runa();

        entidad.setElemento(dto.getElemento());
        entidad.setBonus(dto.getBonus());
        entidad.setNombre(dto.getNombre());
        entidad.setEquipamientos(equipamientoRunaRepository.findAllById(dto.getEquipamientosIds()));

        return entidad;
    }

    public List<EquipamientoRunaEntidad> crearRelacion(Runa runa, RunaDTO dto) {
        if(runa == null ||dto == null || dto.getEquipamientosIds() == null)
        {
            System.out.println("La runa o la lista de ids de equipamientos vienen null");
            return Collections.emptyList();
        }
            List<EquipamientoRunaEntidad> listaRelaciones = new ArrayList<>();
            List<Integer> listaEquipamientos = dto.getEquipamientosIds();

            for (int i = 0 ; i < dto.getEquipamientosIds().size(); i++) {
                EquipamientoRunaEntidad relacion = new EquipamientoRunaEntidad();
                relacion.setRuna(runa);
                relacion.setEquipamiento(equipamientoRepository.getReferenceById(listaEquipamientos.get(i)));
                
                runa.getEquipamientos().add(relacion);
                listaRelaciones.add(relacion);
            }
            System.out.println(listaRelaciones);
            return listaRelaciones;
    }
}
