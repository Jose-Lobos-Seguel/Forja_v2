package com.forja.personal.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.forja.personal.DTO.EquipamientoDTORemoto;


@FeignClient(name = "forja-equipamiento")
public interface EquipamientoClient {

    @GetMapping("/forja/api/v1/equipamientos/{id}")
    List<EquipamientoDTORemoto> obtenerEquipamientosPorIds(@RequestParam("ids") List<Integer> ids);
}