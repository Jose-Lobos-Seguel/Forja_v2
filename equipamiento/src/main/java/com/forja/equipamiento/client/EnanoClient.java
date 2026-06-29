package com.forja.equipamiento.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.forja.equipamiento.DTO.EnanoDTORemoto;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "forja-personal")
public interface EnanoClient {
    
    @GetMapping("/enanos/{id}")
    EnanoDTORemoto buscarPorId(@PathVariable("id") Integer id);
}