package com.forja.recursos.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.forja.recursos.DTO.DimensionDTO;
import com.forja.recursos.modelo.Dimension;
import com.forja.recursos.modelo.MaterialDimension;
import com.forja.recursos.repository.DimensionRepository;
import com.forja.recursos.repository.MaterialDimensionRepository;
import com.forja.recursos.repository.MaterialRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DimensionService {

    private final MaterialRepository materialRepository;
    private final MaterialDimensionRepository materialDimensionRepository;
    private final DimensionRepository dimensionRepository;
    private final EntityManager entityManager;

    public List<DimensionDTO> listar() {
        return dimensionRepository.findAll().stream()
                .map(this::convertirDTO)
                .toList();
    }

    public DimensionDTO buscarPorId(Integer id) {
        Dimension dimension = dimensionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡La dimensión no existe!"));
        return convertirDTO(dimension);
    }

    public DimensionDTO guardar(DimensionDTO dto) {
        Dimension entidadDimension = convertirEntidad(dto);
        Dimension dimensionGuardada = dimensionRepository.save(entidadDimension);
        List<MaterialDimension> relaciones = crearRelacion(dimensionGuardada, dto);
        materialDimensionRepository.saveAll(relaciones);
        return convertirDTO(dimensionGuardada);
    }

    public DimensionDTO actualizar(Integer id, DimensionDTO dimensionActualizada) {
        Dimension dimension = dimensionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar una dimension con el id" + id));

        if(dimensionActualizada.getNombre() != null)
        {
            dimension.setNombre(dimensionActualizada.getNombre());
        }
        if(dimensionActualizada.getDescripcion() != null)
        {
            dimension.setDescripcion(dimensionActualizada.getDescripcion());
        }
        if(dimensionActualizada.getMaterialesIds() != null)
        {
            dimension.getMateriales().clear();
            dimensionRepository.flush();

            List<MaterialDimension> nuevasRelaciones = crearRelacion(dimension,dimensionActualizada);
            dimension.getMateriales().addAll(nuevasRelaciones);
        }
        Dimension dimensionGuardada = dimensionRepository.saveAndFlush(dimension);
        entityManager.refresh(dimensionGuardada);
        return convertirDTO(dimensionGuardada);
    }

    public String eliminarDimension(Integer id) 
    {
        try 
        {
            Dimension dimension = dimensionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La dimension con ID " + id + " no existe."));
            dimensionRepository.delete(dimension);
            return "La dimension" + dimension.getNombre() + "a sido removida de la existencia.";
        }
        catch (RuntimeException e) 
        {
            return e.getMessage();
        }
    }

    private DimensionDTO convertirDTO(Dimension dimension) {
        DimensionDTO dto = new DimensionDTO();
        List<Integer> listaMaterialesIds = new ArrayList<>();
        List<Integer> listaPurezas = new ArrayList<>();

        for (MaterialDimension material : dimension.getMateriales()) {
            listaMaterialesIds.add(material.getMaterial().getId());
            listaPurezas.add(material.getPureza());
        }

        dto.setId(dimension.getId());
        dto.setNombre(dimension.getNombre());
        dto.setDescripcion(dimension.getDescripcion());
        dto.setMaterialesIds(listaMaterialesIds);
        dto.setPurezas(listaPurezas);

        return dto;
    }

    public Dimension convertirEntidad(DimensionDTO dto) {
        Dimension entidad = new Dimension();
        
        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        if(dto.getMaterialesIds() != null)
        {
            entidad.setMateriales(materialDimensionRepository.findAllById(dto.getMaterialesIds()));
        }
        return entidad;
    }

    public List<MaterialDimension> crearRelacion(Dimension dimension, DimensionDTO dto) {
        if(dimension == null ||dto == null || dto.getMaterialesIds() == null || dto.getPurezas() == null)
        {
            System.out.println("Alguna de las listas, dimension o dto viene null ");
            return Collections.emptyList();
        }
        if(dto.getMaterialesIds().size() != dto.getPurezas().size() || dto.getMaterialesIds().size() == 0 || dto.getPurezas().size() == 0)
        {
            System.out.println("La cantidad de materiales y purezas no coinciden o vienen vacias");
            return Collections.emptyList();
        }
            List<MaterialDimension> listaRelaciones = new ArrayList<>();
            List<Integer> listaPureza = dto.getPurezas();
            List<Integer> listaMateriales = dto.getMaterialesIds();

            for (int i = 0 ; i < dto.getMaterialesIds().size(); i++) {
                MaterialDimension relacion = new MaterialDimension();
                relacion.setDimension(dimension);
                relacion.setPureza(listaPureza.get(i));
                relacion.setMaterial(materialRepository.getReferenceById(listaMateriales.get(i)));
                
                dimension.getMateriales().add(relacion);
                listaRelaciones.add(relacion);
            }
            System.out.println(listaRelaciones);
            return listaRelaciones;
    }
}
