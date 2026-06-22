package com.parcial3.personal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial3.personal.DTO.ClanDTO;
import com.parcial3.personal.DTO.EnanoDTO;
import com.parcial3.personal.model.Clan;
import com.parcial3.personal.model.Enano;
import com.parcial3.personal.repository.ClanRepository;
import com.parcial3.personal.repository.EnanoRepository;

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
    
    public Clan guardarClan(Clan clan) {

       return clanRepository.save(clan);
    }

    public List<ClanDTO> buscarPorNombre(String nombre) {

       return clanRepository.findByNombre(nombre)
               .stream()
               .map(this::convertirDTO)
               .toList();
    }

    public List<EnanoDTO> buscarMiembros(Integer clanId) {
    Clan clan = clanRepository.findById(clanId)
            .orElseThrow(() -> new RuntimeException("El clan no existe."));

    return enanoRepository.findByClan(clan)
            .stream()
            .map(this::convertirEnanoDTO)
            .toList();
    }
    public ClanDTO convertirDTO(Clan clan) {
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

    public EnanoDTO convertirEnanoDTO(Enano enano) {
        EnanoDTO dto = new EnanoDTO();

        dto.setId(enano.getId());
        dto.setNombre(enano.getNombre());
        dto.setApellido(enano.getApellido());
        dto.setTitulo(enano.getTitulo());
        dto.setEspecialidad(enano.getEspecialidad());

        if(enano.getClan() != null) {
            dto.setClanId(enano.getClan().getId());
            dto.setClanNombre(enano.getClan().getNombre());
        }

        return dto;
    }
}