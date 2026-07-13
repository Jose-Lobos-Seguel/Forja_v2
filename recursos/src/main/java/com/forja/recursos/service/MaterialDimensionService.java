package com.forja.recursos.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.forja.recursos.DTO.MaterialDimensionDTO;
import com.forja.recursos.modelo.MaterialDimension;
import com.forja.recursos.repository.MaterialDimensionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MaterialDimensionService {
    
    private final MaterialDimensionRepository materialDimensionRepository;

    public List<MaterialDimensionDTO> obtenerRelaciones() {
        return materialDimensionRepository.findAll()
            .stream()
            .map(this::convertirDTO)
            .toList();
    }

    public List<MaterialDimensionDTO> buscarPorIds(List<Integer> materialDimensionIds) {
        return materialDimensionRepository.findAllById(materialDimensionIds)
            .stream()
            .map(this::convertirDTO)
            .toList();
        
    }

    public List<MaterialDimensionDTO> buscarPorIdDeEquipamiento(Integer equipamientoId) {
        return materialDimensionRepository.findAllByEquipamientoId(equipamientoId)
            .stream()
            .map(this::convertirDTO)
            .toList();
    }

    public List<MaterialDimensionDTO> buscarListaPorIdDeEquipamiento(List<Integer> equipamientoId) {
        return materialDimensionRepository.findAllByEquipamientoId(equipamientoId)
            .stream()
            .map(this::convertirDTO)
            .toList();
    }

    public MaterialDimension actualizarPureza(Integer id,Integer nuevaPureza) {

        MaterialDimension materialDimension = materialDimensionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación no encontrada"));

        materialDimension.setPureza(nuevaPureza);

        return materialDimensionRepository.save(materialDimension);
    }

    public void vincularEquipamientoAMateriales(Integer equipamientoId, List<Integer> nuevosMaterialIds) {
        
        List<MaterialDimension> materialesAntiguos = materialDimensionRepository.findAllByEquipamientoId(equipamientoId);
        for (MaterialDimension material : materialesAntiguos) {
            if (!nuevosMaterialIds.contains(material.getId())) {
                material.setEquipamiento_id(null);
            }
        }
        List<MaterialDimension> nuevosMateriales = materialDimensionRepository.findAllById(nuevosMaterialIds);
        
        for (MaterialDimension material : nuevosMateriales) {
            material.setEquipamiento_id(equipamientoId);
        }
        materialDimensionRepository.saveAll(nuevosMateriales);
    }

    public List<Integer> obtenerIdsPorEquipamiento(Integer equipamientoId) {
        List<MaterialDimension> materiales = materialDimensionRepository.findAllByEquipamientoId(equipamientoId);
        
        return materiales
                .stream()
                .filter(Objects::nonNull)
                .map(materialDimension -> materialDimension.getId())
                .filter(Objects::nonNull)
                .toList();
    }

    public MaterialDimensionDTO buscarPorId(Integer id) {
        MaterialDimension material = materialDimensionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación Material-Dimensión no encontrada"));
        return convertirDTO(material);
    }

    private MaterialDimensionDTO convertirDTO(MaterialDimension entity) {
        MaterialDimensionDTO dto = new MaterialDimensionDTO();
        dto.setId(entity.getId());
        dto.setPureza(entity.getPureza());
        
        if (entity.getMaterial() != null) {
            dto.setMaterialId(entity.getMaterial().getId());
            dto.setMaterialNombre(entity.getMaterial().getNombre());
        }
        
        if (entity.getDimension() != null) {
            dto.setDimensionId(entity.getDimension().getId());
            dto.setDimensionNombre(entity.getDimension().getNombre());
        }
        
        return dto;
    }
}
