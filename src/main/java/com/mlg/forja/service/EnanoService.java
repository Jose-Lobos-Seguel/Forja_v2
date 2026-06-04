package com.mlg.forja.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlg.forja.DTO.EnanoDTO;
import com.mlg.forja.modelo.Clan;
import com.mlg.forja.modelo.Enano;
import com.mlg.forja.modelo.Equipamiento;
import com.mlg.forja.repository.ClanRepository;
import com.mlg.forja.repository.EnanoRepository;
import com.mlg.forja.repository.EquipamientoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EnanoService 
{
    @Autowired
    private EnanoRepository enanoRepository;

    @Autowired
    private ClanRepository clanRepository;

    @Autowired
    private EquipamientoRepository equipamientoRepository;

    public List<EnanoDTO> obtenerTodos(){

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
                   .orElseThrow(() -> new RuntimeException("Seria imposible mandar al enano con ID de " + id + " al abismo pues este no existe."));

           enanoRepository.delete(enano);

           return "El enano '" + enano.getNombre() + enano.getTitulo() + enano.getApellido() + "' ha sido enviado al abismo con exito.";

       } catch (RuntimeException e) {

           return e.getMessage();
        }
    }

    public Enano guardarEnano(Enano enano) {

       return enanoRepository.save(enano);
    }

    public List<EnanoDTO> buscarPorNombre(String nombre){

       return enanoRepository.findByNombre(nombre)
               .stream()
               .map(this::convertirDTO)
               .toList();
    }

    public List<EnanoDTO> buscarPorClan(Integer clanId){
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("El clan no existe."));

        return enanoRepository.findByClan(clan)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public List<Equipamiento> obtenerEquipamientosForjados(Integer enanoId)
    {
        Enano enano = enanoRepository.findById(enanoId)
                .orElseThrow(() -> new RuntimeException("El enano no existe."));

        return enano.getEquipamientosForjados();
    }

    public String asignarEquipamientoForjado(Integer enanoId, Integer equipamientoId)
    {
        Enano enano = enanoRepository.findById(enanoId)
                .orElseThrow(() -> new RuntimeException("El enano no existe."));

        Equipamiento equipamiento = equipamientoRepository.findById(equipamientoId)
                .orElseThrow(() -> new RuntimeException("El equipamiento no existe."));

        equipamiento.setForjador(enano);

        equipamientoRepository.save(equipamiento);

        return "El equipamiento '" + equipamiento.getNombre() + "' ahora pertenece al arsenal de " + enano.getNombre() + ".";
    }

    public String removerEquipamientoForjado(Integer equipamientoId)
    {
        Equipamiento equipamiento = equipamientoRepository.findById(equipamientoId)
                .orElseThrow(() -> new RuntimeException("El equipamiento no existe."));

        equipamiento.setForjador(null);

        equipamientoRepository.save(equipamiento);

        return "El equipamiento '" + equipamiento.getNombre() + "' ya no posee un forjador asignado.";
    }

    public EnanoDTO convertirDTO(Enano enano)
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

        if(enano.getEquipamientosForjados() != null)
        {
            List<Integer> equipamientosIds = enano.getEquipamientosForjados()
                    .stream()
                    .map(Equipamiento::getId)
                    .toList();

            dto.setEquipamientosForjadosIds(equipamientosIds);
        }

        return dto;
    }
}
