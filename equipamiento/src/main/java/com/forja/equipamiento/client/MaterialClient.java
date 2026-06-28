package com.forja.equipamiento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "forja-recursos")
public interface MaterialClient {

    @GetMapping("/api/material-dimension/buscar-por-equipamiento")
    List<Integer> obtenerIdsPorEquipamientoId(@RequestParam("equipamientoId") Integer equipamientoId);
}