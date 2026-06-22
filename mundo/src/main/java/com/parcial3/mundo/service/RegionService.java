package com.parcial3.mundo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial3.mundo.DTO.RegionDTO;
import com.parcial3.mundo.model.Region;
import com.parcial3.mundo.repository.RegionRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<RegionDTO> obtenerTodos(){

        return regionRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public RegionDTO buscarPorId(Integer id) {

       Region region = regionRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("La region que esta buscando no ha sido reclamada aun."));

       return convertirDTO(region);
    }

    public String eliminarRegion(Integer id) {

        try {

           Region region = regionRepository.findById(id)
                   .orElseThrow(() -> new RuntimeException("No seria posible eliminar la region con ID de " + id + " del mapa pues esta no ha sido reconocida como tal."));

           regionRepository.delete(region);

           return "La region '" + region.getNombre() +"' ha sido eliminada del mapa con exito.";

       } catch (RuntimeException e) {

           return e.getMessage();
        }
    }

    public Region guardarRegion(Region region) {

       return regionRepository.save(region);
    }

    public List<RegionDTO> buscarPorNombre(String nombre){

       return regionRepository.findByNombre(nombre)
               .stream()
               .map(this::convertirDTO)
               .toList();
    }

    public RegionDTO convertirDTO(Region region)
    {
        RegionDTO dto = new RegionDTO();

        dto.setId(region.getId());
        dto.setNombre(region.getNombre());

        return dto;
    }
}