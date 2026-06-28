package com.mlg.forja.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.EnanoDTO;
import com.mlg.forja.modelo.Clan;
import com.mlg.forja.modelo.Enano;
import com.mlg.forja.modelo.Equipamiento;
import com.mlg.forja.repository.ClanRepository;
import com.mlg.forja.repository.EnanoRepository;
import com.mlg.forja.repository.EquipamientoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EnanoService {

    private final EnanoRepository enanoRepository;
    private final ClanRepository clanRepository;
    private final EquipamientoRepository equipamientoRepository;

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
            if (enano.getClan() != null) 
            {
                enano.getClan().getEnanos().remove(enano);
            }
            if (enano.getEquipamientosForjados() != null) 
            {
                enano.getEquipamientosForjados().clear(); 
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
    if (enanoActualizado.getNombre() != null) 
    {
        enano.setNombre(enanoActualizado.getNombre());
    }
    if (enanoActualizado.getApellido() != null) 
    {
        enano.setApellido(enanoActualizado.getApellido());
    }
    if (enanoActualizado.getTitulo() != null) 
    {
        enano.setTitulo(enanoActualizado.getTitulo());
    }
    
    if (enanoActualizado.getClanId() != null) 
    {
        if (enano.getClan() == null || !enano.getClan().getId().equals(enanoActualizado.getClanId())) 
        {
            if (enano.getClan() != null) 
            {
                enano.getClan().getEnanos().remove(enano);
            }
            Clan nuevoClan = clanRepository.findById(enanoActualizado.getClanId())
                .orElseThrow(() -> new RuntimeException("El nuevo clan especificado no existe"));
                
            enano.setClan(nuevoClan);
            nuevoClan.getEnanos().add(enano);
        }
    } 
    else if (enanoActualizado.getClanId() == null && enano.getClan() != null) 
    {
        enano.getClan().getEnanos().remove(enano);
        enano.setClan(null);
    }
    if (enanoActualizado.getEquipamientosForjadosIds() != null) 
    {
        List<Equipamiento> nuevosEquipamientos = equipamientoRepository.findAllById(enanoActualizado.getEquipamientosForjadosIds());
        //ESTA SECCION NUNCA DEBERIA USARSE PERO POR SI ACASO ESTA
        //UN EQUIPAMIENTO NUNCA DEBERIA SER CAPAZ DE SER CREADO HUERFANO YA QUE EL ID DEL FORJADO NO PUEDE SER NULL EN SU CREACION
        //Y ESTA MARCADO COMO updatable = false
        for (Equipamiento equipo : nuevosEquipamientos) 
        {
            if (equipo.getForjador() == null)
            {
                equipo.setForjador(enano);
                equipo.setNombreForjador(enano.getNombre() + " " + enano.getApellido());
            } 
            // Esta seccion existe para informar que no se le puede asignar el enano al equipamiento porque ya fue forjado
            else if (!equipo.getForjador().getId().equals(enano.getId())) 
            {
                throw new RuntimeException("El equipamiento con ID " + equipo.getId() + " ya fue forjado por otro enano. Es feo robarle el credito");
            }
        }
        enano.setEquipamientosForjados(nuevosEquipamientos);
    }
    
    // 5. Guardar y retornar
    enanoRepository.save(enano);
    return convertirDTO(enano);
    }

    public EnanoDTO convertirDTO(Enano enano) {
        EnanoDTO dto = new EnanoDTO();

        dto.setId(enano.getId());
        dto.setNombre(enano.getNombre());
        dto.setApellido(enano.getApellido());
        dto.setTitulo(enano.getTitulo());

        if(enano.getClan() != null) 
        {
            dto.setClanId(enano.getClan().getId());
            dto.setClanNombre(enano.getClan().getNombre());
        }
        if(enano.getEquipamientosForjados() != null)
        {
            List<Integer> equipamientosIds = enano.getEquipamientosForjados()
                .stream()
                .filter(Objects::nonNull)
                .map(equipamiento -> equipamiento.getId())
                .filter(Objects::nonNull)
                .toList();

            dto.setEquipamientosForjadosIds(equipamientosIds);
        }

        return dto;
    }

    public Enano convertirEntidad(EnanoDTO dto) {
        Enano entidad = new Enano();

        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTitulo(dto.getTitulo());

        if(dto.getClanId() != null)
        {
        Clan clan = clanRepository.findById(dto.getClanId())
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar el clan con ese id"));
        entidad.setClan(clan);
        clan.getEnanos().add(entidad);
        }
        if (dto.getEquipamientosForjadosIds() != null) 
        {
            List<Equipamiento> equipamientos = equipamientoRepository.findAllById(dto.getEquipamientosForjadosIds());
            for (Equipamiento equipo : equipamientos) {
            if (equipo.getForjador() == null)
            {
            equipo.setForjador(entidad);
            equipo.setNombreForjador(entidad.getNombre() + " " + entidad.getApellido());
            }
            }
            entidad.setEquipamientosForjados(equipamientos);
        }
        return entidad;
    }
}
