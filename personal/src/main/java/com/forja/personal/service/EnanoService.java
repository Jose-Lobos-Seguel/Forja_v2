package com.forja.personal.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.forja.personal.DTO.EnanoDTO;
import com.forja.personal.DTO.EquipamientoDTORemoto;
import com.forja.personal.client.EquipamientoClient;
import com.forja.personal.modelo.Clan;
import com.forja.personal.modelo.Enano;
import com.forja.personal.repository.ClanRepository;
import com.forja.personal.repository.EnanoRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class EnanoService {

    private final EnanoRepository enanoRepository;
    private final ClanRepository clanRepository;
    private final EquipamientoClient equipamientoClient;

    public List<EnanoDTO> listar(){
        return enanoRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public EnanoDTO buscarPorId(Integer id) {
       Enano enano = enanoRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("El enano que esta buscando no ha sido esclavizado aun"));
       return convertirDTO(enano);
    }

    public String eliminarEnano(Integer id) {
        try {
           Enano enano = enanoRepository.findById(id)
                   .orElseThrow(() -> new RuntimeException("El enano que se quiere enviar al abismo no existe"));
            
            if (enano.getClan() != null) {
                enano.getClan().getEnanos().remove(enano);
            }

           enanoRepository.delete(enano);
           return "El enano " + enano.getNombre() + " " + enano.getApellido() + " fue lanzado al abismo";

       } catch (RuntimeException e) {
           return e.getMessage();
        }
    }

    public EnanoDTO guardar(EnanoDTO dto) {
        Enano entidadEnano = convertirEntidad(dto);
        enanoRepository.save(entidadEnano);
        return convertirDTO(entidadEnano);
    }

    public EnanoDTO actualizar(Integer id, EnanoDTO enanoActualizado) {
        Enano enano = enanoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un enano con ese id"));
        
        if (enanoActualizado.getNombre() != null) {
            enano.setNombre(enanoActualizado.getNombre());
        }
        if (enanoActualizado.getApellido() != null) {
            enano.setApellido(enanoActualizado.getApellido());
        }
        if (enanoActualizado.getTitulo() != null) {
            enano.setTitulo(enanoActualizado.getTitulo());
        }
        
        if (enanoActualizado.getClanId() != null) {
            if (enano.getClan() == null || !enano.getClan().getId().equals(enanoActualizado.getClanId())) {
                if (enano.getClan() != null) {
                    enano.getClan().getEnanos().remove(enano);
                }
                Clan nuevoClan = clanRepository.findById(enanoActualizado.getClanId())
                    .orElseThrow(() -> new RuntimeException("El nuevo clan especificado no existe"));
                    
                enano.setClan(nuevoClan);
                nuevoClan.getEnanos().add(enano);
            }
        } else if (enanoActualizado.getClanId() == null && enano.getClan() != null) {
            enano.getClan().getEnanos().remove(enano);
            enano.setClan(null);
        }
        
        if (enanoActualizado.getEquipamientosForjadosIds() != null) {
            List<Integer> ids = enanoActualizado.getEquipamientosForjadosIds()
                    .stream()
                    .map(EquipamientoDTORemoto::getId)
                    .toList();
            enano.setEquipamientosForjados_ids(ids);
        }
        
        enanoRepository.save(enano);
        return convertirDTO(enano);
    }

    public EnanoDTO convertirDTO(Enano enano) {
        EnanoDTO dto = new EnanoDTO();
        dto.setId(enano.getId());
        dto.setNombre(enano.getNombre());
        dto.setApellido(enano.getApellido());
        dto.setTitulo(enano.getTitulo());

        if(enano.getClan() != null) {
            dto.setClanId(enano.getClan().getId());
            dto.setClanNombre(enano.getClan().getNombre());
        }
        
        List<EquipamientoDTORemoto> listaEquipamientos = new ArrayList<>();
        if(enano.getEquipamientosForjados_ids() != null && !enano.getEquipamientosForjados_ids().isEmpty()) {
            try {
                listaEquipamientos = equipamientoClient.obtenerEquipamientosPorIds(enano.getEquipamientosForjados_ids());
            } catch (Exception e) {
            }
        }
        dto.setEquipamientosForjadosIds(listaEquipamientos);

        return dto;
    }

    public Enano convertirEntidad(EnanoDTO dto) {
        Enano entidad = new Enano();
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTitulo(dto.getTitulo());

        if(dto.getClanId() != null) {
            Clan clan = clanRepository.findById(dto.getClanId())
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar el clan con ese id"));
            entidad.setClan(clan);
            clan.getEnanos().add(entidad);
        }
        
        // Mapeamos de vuelta los objetos recibidos en el DTO a la lista de IDs de la entidad local
        if (dto.getEquipamientosForjadosIds() != null) {
            List<Integer> ids = dto.getEquipamientosForjadosIds()
                    .stream()
                    .map(EquipamientoDTORemoto::getId)
                    .toList();
            entidad.setEquipamientosForjados_ids(ids);
        }
        
        return entidad;
    }
}