package com.parcial3.forja.service;

import com.parcial3.forja.repository.MaterialRepository;
import com.parcial3.forja.repository.RegionRepository;
import com.parcial3.forja.repository.TipoRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial3.forja.DTO.EquipamientoDTO;
import com.parcial3.forja.model.Equipamiento;
import com.parcial3.forja.model.EquipamientoRunaEntidad;
import com.parcial3.forja.model.MaterialDimension;
import com.parcial3.forja.model.Runa;
import com.parcial3.forja.repository.EnanoRepository;
import com.parcial3.forja.repository.EquipamientoRepository;
import com.parcial3.forja.repository.EquipamientoRunaRepository;
import com.parcial3.forja.repository.MaterialDimensionRepository;
import com.parcial3.forja.repository.RunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EquipamientoService {
    @Autowired
    private EquipamientoRepository equipamientoRepository;

    @Autowired
    private RunaRepository runaRepository;

    @Autowired
    private EquipamientoRunaRepository equipamientoRunaRepository;

    @Autowired
    private EnanoRepository enanoRepository;

    @Autowired
    private MaterialDimensionRepository materialDimensionRepository;

    @Autowired
    private TipoRepository tipoRepository;

    public List<EquipamientoDTO> listar() {
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

    public EquipamientoDTO guardar(EquipamientoDTO dto) {
        Equipamiento equipo = convertirEntidad(dto);
        equipamientoRepository.save(equipo);
        return dto;
    }

    public String eliminar(Integer id) 
    {
        try 
        {
            Equipamiento equipamiento = equipamientoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("El equipamiento con el id " + id + " no existe."));
            equipamientoRepository.delete(equipamiento);
            return "El equipamiento " + equipamiento.getNombre() + " a sido removido del arsenal.";
        }
        catch (RuntimeException e) 
        {
            return e.getMessage();
        }
    }

    public EquipamientoDTO actualizar(Integer id, Equipamiento equipamientoActualizado) {
        Equipamiento equipamiento = equipamientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un equipamiento con el id " + id));
        if(equipamientoActualizado.getNombre() != null)
        {
            equipamiento.setNombre(equipamientoActualizado.getNombre());
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
            equipamiento.setCalidad(calculoCalidad(equipamientoActualizado.getMateriales()));
        }
        equipamientoRepository.save(equipamiento);
        return convertirDTO(equipamiento);
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

    public EquipamientoDTO convertirDTO(Equipamiento equipamiento) {
        EquipamientoDTO dto = new EquipamientoDTO();

        dto.setId(equipamiento.getId());
        dto.setNombre(equipamiento.getNombre());
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
                .map(MaterialDimension::getId)
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

    public Equipamiento convertirEntidad(EquipamientoDTO dto) {
        Equipamiento entidad = new Equipamiento();

        entidad.setNombre(dto.getNombre());
        entidad.setMaxRunas(dto.getMaxRunas());

        entidad.setCalidad(calculoCalidad(materialDimensionRepository.findAllById(dto.getMaterialesIds())));

        if (dto.getTipoId() != null)
        {
            entidad.setTipo(tipoRepository.findById(dto.getTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado")));
        }
        if (dto.getMaterialesIds() != null)
        {
            entidad.setMateriales(materialDimensionRepository.findAllById(dto.getMaterialesIds()));
        }
        if (dto.getRunasIds() != null && dto.getRunasIds().size() <= dto.getMaxRunas())
        {
            List<EquipamientoRunaEntidad> listaRunas = new ArrayList<>();

            for (Integer runaId : dto.getRunasIds() ) {
                Runa runa = runaRepository.findById(runaId)
                    .orElseThrow(() -> new RuntimeException("No se pudo encontrar la runa"));

                EquipamientoRunaEntidad equipamientoRuna = equipamientoRunaRepository.findByRuna(runa)
                    .orElseThrow(() -> new RuntimeException("No se pudo encontrar la relacion relacionada con la runa"));
                
                listaRunas.add(equipamientoRuna);    
            }
            entidad.setRunas(listaRunas);
        }
        if (dto.getForjadorId() != null)
        {
            entidad.setForjador(enanoRepository.findById(dto.getForjadorId())
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar al forjador")));
        }
        return entidad;
    };

    public String calculoCalidad(List<MaterialDimension> materiales) {
        Integer purezaMaxima = materiales.size() * 5;
        Integer purezaTotal = 0;
        for (MaterialDimension material : materiales) {
            purezaTotal += material.getPureza();
        }

        if(purezaTotal == purezaMaxima)
        {
            return "Legendario";
        }
        if(purezaTotal <= purezaMaxima - (purezaMaxima / 5))
        {
            return "Epico";
        }
        if(purezaTotal <= purezaMaxima - (purezaMaxima / 5)*2)
        {
            return "Raro";
        }
        if(purezaTotal <= purezaMaxima - (purezaMaxima / 5)*3)
        {
            return "Poco comun";
        }
        if(purezaTotal <= purezaMaxima - (purezaMaxima / 5)*4)
        {
            return "Comun";
        }
        if(purezaTotal == 0)
        {
            return "Improvisado";
        }
        return null;
    }
}