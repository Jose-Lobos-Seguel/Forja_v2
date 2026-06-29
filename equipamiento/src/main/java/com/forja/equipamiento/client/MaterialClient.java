package com.forja.equipamiento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.forja.equipamiento.DTO.MaterialDimensionDTORemoto;

import java.util.List;

@FeignClient(name = "forja-recursos")
public interface MaterialClient {

    @GetMapping("/api/material-dimension/buscar-por-equipamiento")
    List<Integer> obtenerIdsPorEquipamientoId(@RequestParam("equipamientoId") Integer equipamientoId);

    @PutMapping("/forja/api/v1/material-dimension/vincular-equipamiento")
    void vincularMaterialesAEquipamiento(@RequestParam("equipamientoId") Integer equipamientoId, @RequestBody List<Integer> materialIds);

    @PostMapping("/api/materiales/buscar-por-ids")
    List<MaterialDimensionDTORemoto> obtenerMaterialesPorIds(@RequestBody List<Integer> ids);
}