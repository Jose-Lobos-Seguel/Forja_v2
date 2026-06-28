package com.mlg.forja.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.ClanDTO;
import com.mlg.forja.modelo.Clan;
import com.mlg.forja.modelo.Enano;
import com.mlg.forja.repository.ClanRepository;
import com.mlg.forja.repository.EnanoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClanService {

    private final EnanoRepository enanoRepository;
    private final ClanRepository clanRepository;

    public List<ClanDTO> listar(){

        return clanRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public ClanDTO buscarPorId(Integer id) {

       Clan clan = clanRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("El clan que buscar no a sido conformado aun"));

       return convertirDTO(clan);
    }

    public String eliminarClan(Integer id) {
        try {
           Clan clan = clanRepository.findById(id)
                   .orElseThrow(() -> new RuntimeException("Seria imposible borrar de la existencia al clan con ID de " + id + " pues estos no existen."));
            List<Enano> listaEnanosClan = clan.getEnanos();
                   
            for (Enano enano : listaEnanosClan) {
                enano.setClan(null);
            }

           clanRepository.delete(clan);

           return "El clan " + clan.getNombre() + " se a separado. Ahora cada enano de este clan esta por su cuenta";

       } catch (RuntimeException e) {

           return e.getMessage();
        }
    }
    
    public ClanDTO guardar(ClanDTO dto) {
        Clan entidadClan = convertirEntidad(dto);
        clanRepository.save(entidadClan);
       return convertirDTO(entidadClan);
    }

    public ClanDTO actualizar(Integer id, ClanDTO clanActualizado) {
        Clan clan = clanRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un clan con ese id"));
        
        if(clanActualizado.getNombre() != null)
        {
            clan.setNombre(clanActualizado.getNombre());
        }
        if(clanActualizado.getEnanosIds() != null)
        {
            List<Enano> nuevosEnanos = enanoRepository.findAllById(clanActualizado.getEnanosIds());

            for (Enano enanoAnterior : clan.getEnanos()) {
                enanoAnterior.setClan(null);
            }
            for (Enano enanoNuevo : nuevosEnanos) {
                enanoNuevo.setClan(clan);
            }
            clan.setEnanos(nuevosEnanos);
        }
        clanRepository.save(clan);
        return convertirDTO(clan);
    }

    public ClanDTO convertirDTO(Clan clan) {
        ClanDTO dto = new ClanDTO();

        dto.setId(clan.getId());
        dto.setNombre(clan.getNombre());

        if(clan.getEnanos() != null)
        {
            List<Integer> enanosIds = clan.getEnanos()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(enano -> enano.getId())
                    .filter(Objects::nonNull)
                    .toList();

            dto.setEnanosIds(enanosIds);
        }

        return dto;
    }

    public Clan convertirEntidad(ClanDTO dto) {
        Clan entidad = new Clan();
        entidad.setNombre(dto.getNombre());

        if(dto.getEnanosIds() != null)
        {
            List<Enano> enanos = enanoRepository.findAllById(dto.getEnanosIds());
            for (Enano enano : enanos) {
                enano.setClan(entidad);
            }
            entidad.setEnanos(enanos);
        }
        return entidad;
    }
}