package com.mlg.forja.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.MaterialDimensionDTO;
import com.mlg.forja.modelo.MaterialDimension;
import com.mlg.forja.repository.MaterialDimensionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MaterialDimensionService {
    
    private final MaterialDimensionRepository materialDimensionRepository;

    public List<MaterialDimensionDTO> obtenerRelaciones() {
        return materialDimensionRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public MaterialDimension actualizarPureza(Integer id,Integer nuevaPureza) 
    {

        MaterialDimension materialDimension =
                materialDimensionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Relación no encontrada"));

        materialDimension.setPureza(nuevaPureza);

        return materialDimensionRepository.save(materialDimension);
    }

    public String eliminarRelacion(Integer id) 
    {
        MaterialDimension relacion =
                materialDimensionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Relación no encontrada"));

        materialDimensionRepository.delete(relacion);

        return "Relación eliminada correctamente";
    }

    public MaterialDimensionDTO buscarPorId(Integer id) {
        MaterialDimension mds = materialDimensionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Relación Material-Dimensión no encontrada!"));
        return convertirADTO(mds);
    }

    private MaterialDimensionDTO convertirADTO(MaterialDimension entity) {
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
