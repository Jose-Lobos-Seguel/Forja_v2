package com.parcial3.forja.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial3.forja.DTO.MaterialDTO;
import com.parcial3.forja.model.Material;
import com.parcial3.forja.model.MaterialDimension;
import com.parcial3.forja.repository.DimensionRepository;
import com.parcial3.forja.repository.MaterialDimensionRepository;
import com.parcial3.forja.repository.MaterialRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaterialService {
    @Autowired
    private MaterialDimensionRepository materialDimensionRepository;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public MaterialDTO guardar(MaterialDTO dto) {
        Material entidad = convertirEntidad(dto);
        List<MaterialDimension> listaRelaciones = new ArrayList<>();
        if(crearRelacion(dto) != null)
        {
            listaRelaciones.addAll(crearRelacion(dto));
            for (MaterialDimension relacion : listaRelaciones) {
                materialDimensionRepository.save(relacion);                
            }
        }
        materialRepository.save(entidad);
        return dto;
    }

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

    public MaterialDTO actualizar(Integer id, MaterialDTO materialActualizado) {
        Material entidadActualizada = convertirEntidad(materialActualizado);
        Material material = materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un material con ese id"));
        
        if(entidadActualizada.getNombre() != null)
        {
            material.setNombre(entidadActualizada.getNombre());
        }
        if(entidadActualizada.getDimensiones() != null)
        {
            material.setDimensiones(entidadActualizada.getDimensiones());
        }
        materialRepository.save(material);
        return convertirDTO(material);
    }

    public String eliminar(Integer id) {
        try {
            Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar el material con ese id"));
            materialRepository.delete(material);

            List<MaterialDimension> relaciones = materialDimensionRepository.findAllByMaterial(material);
            List<Integer> relacionesIds = relaciones.stream()
                                                    .map(MaterialDimension::getId)
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
            listaDimensionesId.add(dimension.getId());
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
        entidad.setId(dto.getId());
        entidad.setNombre(dto.getNombre());
        entidad.setDimensiones(materialDimensionRepository.findAllById(dto.getDimensionesIds()));

        return entidad;
    }

    public List<MaterialDimension> crearRelacion(MaterialDTO material) {
        if((material.getDimensionesIds() != null && material.getPurezas() != null) && (material.getDimensionesIds().size() == material.getPurezas().size()))
        {
            List<MaterialDimension> listaRelaciones = new ArrayList<>();
            List<Integer> listaPureza = new ArrayList<>();
            List<Integer> listaDimensiones = new ArrayList<>();

            listaPureza.addAll(material.getPurezas());
            listaDimensiones.addAll(material.getDimensionesIds());

            for (int i = 0 ; i < material.getDimensionesIds().size() - 1; i++) {
                MaterialDimension relacion = new MaterialDimension();
                int pureza = listaPureza.get(i);
                int dimensionId = listaDimensiones.get(i);

                relacion.setMaterial(materialRepository.findById(material.getId())
                    .orElseThrow(() -> new RuntimeException("No se pudo encontrar el material por el id " + material.getId())));

                relacion.setPureza(pureza);
                relacion.setDimension(dimensionRepository.findById(dimensionId)
                    .orElseThrow(() -> new RuntimeException("No se pudo encontrar la dimension por el id " + dimensionId)));

                listaRelaciones.add(relacion);
            }
            return listaRelaciones;
        }
        System.out.println("La lista de dimensiones viene con ids de dimensiones que no existen, alguna lista viene null o hay cantidades diferentes de listas y purezas");
        return null;
    }
}