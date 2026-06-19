package com.mlg.forja.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.ClanDTO;
import com.mlg.forja.DTO.EnanoDTO;
import com.mlg.forja.modelo.Clan;
import com.mlg.forja.modelo.Enano;
import com.mlg.forja.repository.ClanRepository;
import com.mlg.forja.repository.EnanoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClanService {

    @Autowired
    private EnanoRepository enanoRepository;

    @Autowired
    private ClanRepository clanRepository;

    public List<ClanDTO> obtenerTodos(){

        return clanRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public ClanDTO buscarPorId(Integer id) {

       Clan clan = clanRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("El clan de enanos que esta buscando no ha sido esclavizado aun"));

       return convertirDTO(clan);
    }

    public String eliminarClan(Integer id) {

        try {

           Clan clan = clanRepository.findById(id)
                   .orElseThrow(() -> new RuntimeException("Seria imposible borrar de la existencia al clan con ID de " + id + " pues estos no existen."));

           clanRepository.delete(clan);

           return "El clan '" + clan.getNombre() + "' ha sido eliminado de esta existencia exitosamente.";

       } catch (RuntimeException e) {

           return e.getMessage();
        }
    }
    
    public ClanDTO guardarClan(ClanDTO clanDTO) {

       Clan clan = convertirEntidad(clanDTO);
       clanRepository.save(clan);
       return clanDTO;
    }

    public ClanDTO actualizarClan(Integer id, ClanDTO clanDTO) {

       Clan clan = clanRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("El clan de enanos que intenta actualizar no existe"));

       if(clanDTO.getNombre() != null) {
           clan.setNombre(clanDTO.getNombre());
       }
       clanRepository.save(clan);
       return convertirDTO(clan);
    }

    public List<ClanDTO> buscarPorNombre(String nombre){

       return clanRepository.findByNombre(nombre)
               .stream()
               .map(this::convertirDTO)
               .toList();
    }

    public List<EnanoDTO> buscarMiembros(Integer clanId)
    {
    Clan clan = clanRepository.findById(clanId)
            .orElseThrow(() -> new RuntimeException("El clan no existe."));

    return enanoRepository.findByClan(clan)
            .stream()
            .map(this::convertirEnanoDTO)
            .toList();
    }
    private Clan convertirEntidad(ClanDTO dto) {
        Clan clan = new Clan();
        clan.setId(dto.getId());
        clan.setNombre(dto.getNombre());
        return clan;
    }

    public ClanDTO convertirDTO(Clan clan)
    {
        ClanDTO dto = new ClanDTO();

        dto.setId(clan.getId());
        dto.setNombre(clan.getNombre());

        if(clan.getEnanos() != null)
        {
            List<Integer> enanosIds = clan.getEnanos()
                    .stream()
                    .map(Enano::getId)
                    .toList();

            dto.setEnanosIds(enanosIds);
        }

        return dto;
    }

    public EnanoDTO convertirEnanoDTO(Enano enano)
    {
        EnanoDTO dto = new EnanoDTO();

        dto.setId(enano.getId());
        dto.setNombre(enano.getNombre());
        dto.setApellido(enano.getApellido());
        dto.setTitulo(enano.getTitulo());
        dto.setEspecialidad(enano.getEspecialidad());

        if(enano.getClan() != null)
        {
            dto.setClanId(enano.getClan().getId());
            dto.setClanNombre(enano.getClan().getNombre());
        }

        return dto;
    }
}