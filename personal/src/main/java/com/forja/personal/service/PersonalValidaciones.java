package com.forja.personal.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import com.forja.personal.DTO.EquipamientoDTORemoto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PersonalValidaciones {

    private final WebClient.Builder webClientBuilder;

    @SuppressWarnings("null")
    public List<EquipamientoDTORemoto> obtenerEquipamiento(Integer id){
        try {
            EquipamientoDTORemoto[] resultado = webClientBuilder.build()
                .get()
                .uri("http:///forja/api/v1/equipamientos/buscar-por-enano/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToMono(EquipamientoDTORemoto[].class)
                .block();

            if (resultado != null) {
                return Arrays.asList(resultado);
            }
            else {
                return List.of();
            }

        } catch (Exception e) {
            System.out.println("El sistema con el que se esta intentando comunicar, esta caido");
            return List.of();
        }
    }
}
