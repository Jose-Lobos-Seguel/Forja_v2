package com.forja.recursos.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.forja.recursos.DTO.MaterialDTO;
import com.forja.recursos.modelo.Material;
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
public class MaterialService 
{

    private final MaterialRepository materialRepository;
    private final MaterialDimensionRepository materialDimensionRepository;
    private final DimensionRepository dimensionRepository;
    private final EntityManager entityManager;

    public List<MaterialDTO> listar() {
        return materialRepository.findAll()
            .stream()
            .map(this::convertirDTO)
            .toList();
    }

    public MaterialDTO buscarPorId(Integer id) {
        Material material = materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un material con ese id"));
        
        return convertirDTO(material);
    }

    public MaterialDTO guardar(MaterialDTO dto) {
        Material entidadMaterial = convertirEntidad(dto);
        Material materialGuardado = materialRepository.save(entidadMaterial);
        List<MaterialDimension> relaciones = crearRelacion(materialGuardado, dto);
        materialDimensionRepository.saveAll(relaciones);
        return convertirDTO(materialGuardado); 
    }

    public MaterialDTO actualizar(Integer id, MaterialDTO materialActualizado) {
        Material material = materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un material con ese id"));
        
        if(materialActualizado.getNombre() != null)
        {
            material.setNombre(materialActualizado.getNombre());
        }
        if(materialActualizado.getDimensionesIds() != null)
        {
            material.getDimensiones().clear();
            materialRepository.flush();

            List<MaterialDimension> nuevasRelaciones = crearRelacion(material, materialActualizado);
            material.getDimensiones().addAll(nuevasRelaciones);
        }
        Material materialGuardado = materialRepository.saveAndFlush(material);
        entityManager.refresh(materialGuardado);
        return convertirDTO(materialGuardado);
    }

    public String eliminar(Integer id) {
        try {
            Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar el material con ese id"));
            materialRepository.delete(material);
            List<MaterialDimension> relaciones = materialDimensionRepository.findAllByMaterial(material);
            List<Integer> relacionesIds = relaciones.stream()
                                                    .filter(Objects::nonNull)
                                                    .map(materialDimension -> materialDimension.getId())
                                                    .filter(Objects::nonNull)
                                                    .toList();                
            materialDimensionRepository.deleteAllByIdInBatch(relacionesIds);

            return "El material " + material.getNombre() + " a sido eliminado junto a todas sus relaciones";
        }
        catch(RuntimeException e) {
            return e.getMessage();
        }
    }

    public MaterialDTO convertirDTO(Material material) {
        MaterialDTO dto = new MaterialDTO();
        List<Integer> listaDimensionesId = new ArrayList<>();
        List<Integer> listaPurezas = new ArrayList<>();

        for (MaterialDimension dimension : material.getDimensiones()) {
            listaDimensionesId.add(dimension.getDimension().getId());
            listaPurezas.add(dimension.getPureza());
        }

        dto.setId(material.getId());
        dto.setNombre(material.getNombre());
        dto.setDimensionesIds(listaDimensionesId);
        dto.setPurezas(listaPurezas);

        return dto;
    }

    public Material convertirEntidad(MaterialDTO dto) {
        Material entidad = new Material();

        entidad.setNombre(dto.getNombre());
        if(dto.getDimensionesIds() != null)
        {
            entidad.setDimensiones(materialDimensionRepository.findAllById(dto.getDimensionesIds()));
        }

        return entidad;
    }

    public List<MaterialDimension> crearRelacion(Material material, MaterialDTO dto) {
        if(material == null ||dto == null || dto.getDimensionesIds() == null || dto.getPurezas() == null)
        {
            System.out.println("Alguna de las listas, material o dto viene null ");
            return Collections.emptyList();
        }
        if(dto.getDimensionesIds().size() != dto.getPurezas().size() || dto.getDimensionesIds().size() == 0 || dto.getPurezas().size() == 0)
        {
            System.out.println("La cantidad de dimensiones y purezas no coinciden o vienen vacias");
            return Collections.emptyList();
        }
            List<MaterialDimension> listaRelaciones = new ArrayList<>();
            List<Integer> listaPureza = dto.getPurezas();
            List<Integer> listaDimensiones = dto.getDimensionesIds();

            for (int i = 0 ; i < dto.getDimensionesIds().size(); i++) {
                MaterialDimension relacion = new MaterialDimension();
                relacion.setMaterial(material);
                relacion.setPureza(listaPureza.get(i));
                relacion.setDimension(dimensionRepository.getReferenceById(listaDimensiones.get(i)));
                
                material.getDimensiones().add(relacion);
                listaRelaciones.add(relacion);
            }
            System.out.println(listaRelaciones);
            return listaRelaciones;
    }
}