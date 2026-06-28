package com.forja.equipamiento.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.forja.equipamiento.DTO.EnanoDTORemoto;
import com.forja.equipamiento.DTO.EquipamientoDTO;
import com.forja.equipamiento.client.EnanoClient;
import com.forja.equipamiento.client.MaterialClient;
import com.forja.equipamiento.modelo.Equipamiento;
import com.forja.equipamiento.modelo.EquipamientoRunaEntidad;
import com.forja.equipamiento.repository.EquipamientoRepository;
import com.forja.equipamiento.repository.EquipamientoRunaRepository;
import com.forja.equipamiento.repository.RunaRepository;
import com.forja.equipamiento.repository.TipoRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipamientoService {

    private final EquipamientoRepository equipamientoRepository;
    private final EquipamientoRunaRepository equipamientoRunaRepository;
    private final TipoRepository tipoRepository;
    private final RunaRepository runaRepository;
    private final EntityManager entityManager;

    //Clientes HTTP
    private final EnanoClient enanoClient;
    private final MaterialClient materialClient;

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
        Equipamiento entidadEquipamiento = convertirEntidad(dto);
        Equipamiento equipamientoGuardado = equipamientoRepository.save(entidadEquipamiento);
        List<EquipamientoRunaEntidad> relaciones = crearRelacion(equipamientoGuardado, dto);
        equipamientoRunaRepository.saveAll(relaciones);
        return convertirDTO(equipamientoGuardado);
    }

    public String eliminar(Integer id) {
        try {
            Equipamiento equipamiento = equipamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar el equipamiento con ese id"));

            equipamientoRepository.delete(equipamiento);

            return "El equipamiento " + equipamiento.getNombre() + " a sido eliminado junto a todas sus relaciones";
        }
        catch(RuntimeException e) {
            return e.getMessage();
        }
    }

    public EquipamientoDTO actualizar(Integer id, EquipamientoDTO equipamientoActualizado) {
        Equipamiento equipamiento = equipamientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un material con ese id"));
        
        if(equipamientoActualizado.getNombre() != null)
        {
            equipamiento.setNombre(equipamientoActualizado.getNombre());
        }
        if(equipamientoActualizado.getMaxRunas() != null)
        {
            equipamiento.setMaxRunas(equipamientoActualizado.getMaxRunas());
        }
        if(equipamientoActualizado.getForjadorId() != null)
        {
            equipamiento.setForjador(enanoRepository.findById(equipamientoActualizado.getForjadorId())
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar al enano con ese id")));
        }
        if(equipamientoActualizado.getTipoId() != null)
        {
            equipamiento.setTipo(tipoRepository.findById(equipamientoActualizado.getTipoId())
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar el tipo con ese id")));  
        }
        if(equipamientoActualizado.getMaterialDimensionIds() != null)
        {
            equipamiento.setMateriales(materialDimensionRepository.findAllById(equipamientoActualizado.getMaterialDimensionIds())); 
        }
        if(equipamientoActualizado.getRunasIds() != null)
        {
            equipamiento.getRunas().clear();
            equipamientoRepository.flush();

            List<EquipamientoRunaEntidad> nuevasRelaciones = crearRelacion(equipamiento, equipamientoActualizado);
            equipamiento.getRunas().addAll(nuevasRelaciones);
        }
        Equipamiento equipamientoGuardado = equipamientoRepository.saveAndFlush(equipamiento);
        entityManager.refresh(equipamientoGuardado);
        return convertirDTO(equipamientoGuardado);
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

    if (equipamiento.getRunas() != null) 
    {
        List<Integer> runasIds = equipamiento.getRunas()
            .stream()
            .filter(Objects::nonNull)
            .map(equipamientoRuna -> equipamientoRuna.getId())
            .filter(Objects::nonNull)
            .toList();

        dto.setRunasIds(runasIds);
    }

    if (equipamiento.getForjador_id() != null) 
    {
        dto.setForjadorId(equipamiento.getForjador_id());
        try {
            EnanoDTORemoto forjadorRemoto = enanoClient.obtenerEnanoPorId(equipamiento.getForjador_id());
            
            dto.setForjadorId(forjadorRemoto.getId()); 
            dto.setNombreForjador(forjadorRemoto.getNombre());
        } catch (Exception e) {
            dto.setNombreForjador(equipamiento.getNombreForjador());
        }
    }
    try {
        List<Integer> materialesIds = materialClient.obtenerIdsPorEquipamientoId(equipamiento.getId());
        dto.setMaterialDimensionIds(materialesIds);
    } catch (Exception e) {
        dto.setMaterialDimensionIds(new ArrayList<>());
    }
    return dto;
    }

    public Equipamiento convertirEntidad(EquipamientoDTO dto) {
        Equipamiento entidad = new Equipamiento();

        entidad.setNombre(dto.getNombre());
        entidad.setMaxRunas(dto.getMaxRunas());
        entidad.setNombreForjador(dto.getNombreForjador());

        if (dto.getTipoId() != null)
        {
            entidad.setTipo(tipoRepository.findById(dto.getTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado")));
        }
        if (dto.getMaterialDimensionIds() != null)
        {
            entidad.setMateriales(materialDimensionRepository.findAllById(dto.getMaterialDimensionIds()));
            entidad.setCalidad(calculoCalidad(materialDimensionRepository.findAllById(dto.getMaterialDimensionIds())));
        }

        if (dto.getRunasIds() != null && dto.getRunasIds().size() <= dto.getMaxRunas())
        {
            entidad.setRunas(equipamientoRunaRepository.findAllById(dto.getRunasIds()));
        }
        if (dto.getForjadorId() != null)
        {
            entidad.setForjador(enanoRepository.findById(dto.getForjadorId())
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar al forjador")));
        }
        return entidad;
    }

    public List<EquipamientoRunaEntidad> crearRelacion(Equipamiento equipamiento, EquipamientoDTO dto) {
        if(equipamiento == null ||dto == null || dto.getRunasIds() == null)
        {
            System.out.println("El equipamiento o la lista de materialDimensionIds viene null");
            return Collections.emptyList();
        }
            List<EquipamientoRunaEntidad> listaRelaciones = new ArrayList<>();
            List<Integer> listaRunas = dto.getRunasIds();

            for (int i = 0 ; i < dto.getMaterialDimensionIds().size(); i++) {
                EquipamientoRunaEntidad relacion = new EquipamientoRunaEntidad();

                relacion.setEquipamiento(equipamiento);
                relacion.setRuna(runaRepository.getReferenceById(listaRunas.get(i)));
                
                equipamiento.getRunas().add(relacion);
                listaRelaciones.add(relacion);
            }
            System.out.println(listaRelaciones);
            return listaRelaciones;
    }

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