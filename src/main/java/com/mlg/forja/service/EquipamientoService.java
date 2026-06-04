package com.mlg.forja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.EquipamientoDTO;
import com.mlg.forja.modelo.Enano;
import com.mlg.forja.modelo.Equipamiento;
import com.mlg.forja.modelo.EquipamientoRunaEntidad;
import com.mlg.forja.modelo.Material;
import com.mlg.forja.modelo.Runa;
import com.mlg.forja.repository.EnanoRepository;
import com.mlg.forja.repository.EquipamientoRepository;
import com.mlg.forja.repository.EquipamientoRunaRepository;
import com.mlg.forja.repository.RunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EquipamientoService 
{
    @Autowired
    private EquipamientoRepository equipamientoRepository;

    @Autowired
    private RunaRepository runaRepository;

    @Autowired
    private EquipamientoRunaRepository equipamientoRunaRepository;

    @Autowired
    private EnanoRepository enanoRepository;

    public List<EquipamientoDTO> obtenerTodos() {
        return equipamientoRepository.findAll()
            .stream()
            .map(this::convertirDTO)
            .toList();
    }

    public EquipamientoDTO buscarPorId(Integer id) {
        Equipamiento equipamiento = equipamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Equipamiento no encontrado!"));
        return convertirDTO(equipamiento);
    }

    public Equipamiento guardar(Equipamiento equipo) 
    {
        return equipamientoRepository.save(equipo);
    }

    public String eliminarMaterial(Integer id) 
    {
        try 
        {
            Equipamiento equipamiento = equipamientoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La dimension con ID " + id + " no existe."));
            equipamientoRepository.delete(equipamiento);
            return "El equipamiento " + equipamiento.getNombre() + " a sido removido del arsenal.";
        }
        catch (RuntimeException e) 
        {
            return e.getMessage();
        }
    }

    public Equipamiento updateEquipamiento(Integer id, Equipamiento equipamientoActualizado)
    {
        Equipamiento equipamiento = equipamientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un equipamiento con el id " + id));

        if(equipamientoActualizado.getNombre() != null)
        {
            equipamiento.setNombre(equipamientoActualizado.getNombre());
        }
        if(equipamientoActualizado.getCalidad() != null)
        {
            equipamiento.setCalidad(equipamientoActualizado.getCalidad());
        }
        if(equipamientoActualizado.getMaxRunas() != null)
        {
            equipamiento.setMaxRunas(equipamientoActualizado.getMaxRunas());
        }
        if(equipamientoActualizado.getTipo() != null)
        {
            equipamiento.setTipo(equipamientoActualizado.getTipo());
        }
        if(equipamientoActualizado.getRunas() != null)
        {
            equipamiento.setRunas(equipamientoActualizado.getRunas());
        }
        if(equipamientoActualizado.getMateriales() != null)
        {
            equipamiento.setMateriales(equipamientoActualizado.getMateriales());
        }
        if(equipamientoActualizado.getForjador() != null)
        {
            equipamiento.setForjador(equipamientoActualizado.getForjador());
        }

        return equipamientoRepository.save(equipamiento);
    }

    public String colocarRuna(Integer equipamientoId, Integer runaId, String bonus) {

        Equipamiento equipo = equipamientoRepository.findById(equipamientoId)
                .orElseThrow(() -> new RuntimeException("El equipamiento no existe."));
        
        Runa runa = runaRepository.findById(runaId)
                .orElseThrow(() -> new RuntimeException("La runa no existe."));

        if (equipo.getRunas() != null && equipo.getRunas().size() >= equipo.getMaxRunas()) {
            throw new RuntimeException("¡Forja fallida! Límite de runas alcanzado.");
        }

        EquipamientoRunaEntidad relacion = new EquipamientoRunaEntidad(); 

        relacion.setEquipamiento(equipo);
        relacion.setRuna(runa);
        relacion.setBonus(bonus);

        equipamientoRunaRepository.save(relacion);
        
        return "Runa colocada con éxito.";
    }

    public String asignarForjador(Integer equipamientoId, Integer enanoId)
    {
        Equipamiento equipamiento = equipamientoRepository.findById(equipamientoId)
                .orElseThrow(() -> new RuntimeException("El equipamiento no existe."));

        Enano enano = enanoRepository.findById(enanoId)
                .orElseThrow(() -> new RuntimeException("El enano no existe."));

        equipamiento.setForjador(enano);

        equipamientoRepository.save(equipamiento);

        return "El equipamiento '" + equipamiento.getNombre() + "' fue forjado por " + enano.getNombre() + ".";
    }

    public String removerForjador(Integer equipamientoId)
    {
        Equipamiento equipamiento = equipamientoRepository.findById(equipamientoId)
                .orElseThrow(() -> new RuntimeException("El equipamiento no existe."));

        equipamiento.setForjador(null);

        equipamientoRepository.save(equipamiento);

        return "El equipamiento '" + equipamiento.getNombre() + "' ya no posee un forjador asignado.";
    }

    public EquipamientoDTO convertirDTO(Equipamiento equipamiento) 
    {

        EquipamientoDTO dto = new EquipamientoDTO();

        dto.setId(equipamiento.getId());
        dto.setNombre(equipamiento.getNombre());
        dto.setCalidad(equipamiento.getCalidad());
        dto.setMaxRunas(equipamiento.getMaxRunas());

        if (equipamiento.getTipo() != null) 
        {

            dto.setTipoId(equipamiento.getTipo().getId());
            dto.setTipoNombre(equipamiento.getTipo().getNombre());
        }

        if(equipamiento.getForjador() != null)
        {
            dto.setForjadorId(equipamiento.getForjador().getId());
            dto.setForjadorNombre(equipamiento.getForjador().getNombre());
        }

        if (equipamiento.getMateriales() != null) 
        {

            List<Integer> materialesIds = equipamiento.getMateriales().stream()
                .map(Material::getId)
                .toList();

            dto.setMaterialesIds(materialesIds);
        }
        
        if (equipamiento.getRunas() != null) 
        {

            List<Integer> runasIds = equipamiento.getRunas().stream()
                .map(EquipamientoRunaEntidad::getId)
                .toList();

            dto.setRunasIds(runasIds);
        }

        return dto;
    }
}