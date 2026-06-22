package com.parcial3.mundo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial3.mundo.DTO.DimensionDTO;
import com.parcial3.mundo.model.Dimension;
import com.parcial3.mundo.repository.DimensionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DimensionService {
    @Autowired
    private DimensionRepository dimensionRepository;

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

    public Dimension guardarDimension(Dimension dimension)
    {
        return dimensionRepository.save(dimension);
    }

    public Dimension actualizarDimension(Integer id, Dimension dimensionActualizada)
    {
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
        if(dimensionActualizada.getMateriales() != null)
        {
            dimension.setMateriales(dimensionActualizada.getMateriales());
        }
        return dimensionRepository.save(dimension);
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
