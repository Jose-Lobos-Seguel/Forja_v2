package com.mlg.forja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.DimensionDTO;
import com.mlg.forja.modelo.Dimension;
import com.mlg.forja.modelo.MaterialDimension;
import com.mlg.forja.repository.DimensionRepository;
import com.mlg.forja.repository.MaterialDimensionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DimensionService {
    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private MaterialDimensionRepository materialDimensionRepository;

    public List<DimensionDTO> obtenerTodas() {
        return dimensionRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public DimensionDTO buscarPorId(Integer id) {
        Dimension dimension = dimensionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡La dimensión no existe!"));
        return convertirADTO(dimension);
    }

    public DimensionDTO guardarDimension(DimensionDTO dimensionDTO)
    {
        Dimension dimension = convertirEntidad(dimensionDTO);
        dimensionRepository.save(dimension);
        
        // Vincular MaterialDimensions si se proporcionan
        if(dimensionDTO.getMaterialDimensionIds() != null && !dimensionDTO.getMaterialDimensionIds().isEmpty()) {
            List<MaterialDimension> materiales = materialDimensionRepository.findAllById(dimensionDTO.getMaterialDimensionIds());
            for(MaterialDimension materialDim : materiales) {
                materialDim.setDimension(dimension);
                materialDimensionRepository.save(materialDim);
            }
        }
        
        return convertirADTO(dimension);
    }

    public DimensionDTO actualizarDimension(Integer id, DimensionDTO dimensionDTO)
    {
        Dimension dimension = dimensionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar una dimension con el id" + id));

        if(dimensionDTO.getNombre() != null)
        {
            dimension.setNombre(dimensionDTO.getNombre());
        }
        if(dimensionDTO.getDescripcion() != null)
        {
            dimension.setDescripcion(dimensionDTO.getDescripcion());
        }
        
        // Vincular MaterialDimensions si se proporcionan
        if(dimensionDTO.getMaterialDimensionIds() != null && !dimensionDTO.getMaterialDimensionIds().isEmpty()) {
            List<MaterialDimension> materiales = materialDimensionRepository.findAllById(dimensionDTO.getMaterialDimensionIds());
            for(MaterialDimension materialDim : materiales) {
                materialDim.setDimension(dimension);
                materialDimensionRepository.save(materialDim);
            }
        }
        
        dimensionRepository.save(dimension);
        return convertirADTO(dimension);
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

    private Dimension convertirEntidad(DimensionDTO dto) {
        Dimension dimension = new Dimension();
        dimension.setId(dto.getId());
        dimension.setNombre(dto.getNombre());
        dimension.setDescripcion(dto.getDescripcion());
        return dimension;
    }

    private DimensionDTO convertirADTO(Dimension dimension) {
        DimensionDTO dto = new DimensionDTO();
        dto.setId(dimension.getId());
        dto.setNombre(dimension.getNombre());
        dto.setDescripcion(dimension.getDescripcion());
        
        if (dimension.getMateriales() != null) {
            dto.setNombresMateriales(dimension.getMateriales().stream()
                    .map(md -> md.getMaterial().getNombre())
                    .toList());
        }
        return dto;
    }
}
