package com.mlg.forja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.MaterialDTO;
import com.mlg.forja.modelo.Dimension;
import com.mlg.forja.modelo.Material;
import com.mlg.forja.modelo.MaterialDimension;
import com.mlg.forja.repository.DimensionRepository;
import com.mlg.forja.repository.MaterialDimensionRepository;
import com.mlg.forja.repository.MaterialRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaterialService 
{
    @Autowired
    private MaterialDimensionRepository materialDimensionRepository;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<MaterialDTO> obtenerTodos() {
        return materialRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public MaterialDTO buscarPorId(Integer id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Material no encontrado!"));
        return convertirADTO(material);
    }

    public Material guardar(Material material) {
        return materialRepository.save(material);
    }

    public String eliminarMaterial(Integer id) 
    {
        try 
        {
            Material material = materialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La dimension con ID " + id + " no existe."));
            materialRepository.delete(material);
            return "El material" + material.getNombre() + "a sido removido de la existencia.";
        }
        catch (RuntimeException e) 
        {
            return e.getMessage();
        }
    }

    public String añadirDimensionAMaterial(Integer materialId, Integer dimensionId) 
    {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> 
                        new RuntimeException("Material no encontrado"));

        Dimension dimension = dimensionRepository.findById(dimensionId)
                .orElseThrow(() -> 
                        new RuntimeException("Dimensión no encontrada"));

        MaterialDimension materialDimension = new MaterialDimension();

        materialDimension.setMaterial(material);
        materialDimension.setDimension(dimension);

        materialDimensionRepository.save(materialDimension);

        return "Dimensión asignada correctamente";
    }

    //Metodo que hace que un material ya no aparezca en la dimension indicada
    public String desasignarMaterialDeDimension(Integer materialId,Integer dimensionId) 
    {

        MaterialDimension relacion = materialDimensionRepository
                .findByMaterialIdAndDimensionId(materialId, dimensionId)
                .orElseThrow(() ->
                        new RuntimeException("La relación no existe"));

        materialDimensionRepository.delete(relacion);

        return "Dimensión desasignada correctamente";
    }

    private MaterialDTO convertirADTO(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setId(material.getId());
        dto.setNombre(material.getNombre());
        
        if (material.getEquipamiento() != null) {
            dto.setEquipamientoId(material.getEquipamiento().getId());
            dto.setNombreEquipamiento(material.getEquipamiento().getNombre());
        }
        
        if (material.getDimensiones() != null) {
            dto.setNombresDimensiones(material.getDimensiones().stream()
                    .map(md -> md.getDimension().getNombre())
                    .toList());
        }
        return dto;
    }
}
