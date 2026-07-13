package com.forja.equipamiento.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import com.forja.equipamiento.DTO.EnanoDTORemoto;
import com.forja.equipamiento.DTO.MaterialDimensionDTORemoto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class EquipamientoValidaciones {

    private final WebClient.Builder webClientBuilder;

    @SuppressWarnings("null")
    public List<MaterialDimensionDTORemoto> obtenerMateriales(Integer id) {
        try {
            MaterialDimensionDTORemoto[] resultado = webClientBuilder.build()
                .get()
                .uri("http://forja/api/v1/material-dimension/buscar-por-equipamiento/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToMono(MaterialDimensionDTORemoto[].class)
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

    //A diferencia del metodo anterior devuelve una lista de materiales que coinciden con la lista de ids entregados
    @SuppressWarnings("null")
    public List<MaterialDimensionDTORemoto> obtenerListaMateriales(List<Integer> id) {
        try {
            MaterialDimensionDTORemoto[] resultado = webClientBuilder.build()
                .get()
                .uri("http://forja/api/v1/material-dimension/buscar-lista-por-ids/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToMono(MaterialDimensionDTORemoto[].class)
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

    @SuppressWarnings("null")
    public EnanoDTORemoto obtenerForjador(Integer id) {
        EnanoDTORemoto enanoRecuperado = new EnanoDTORemoto();
        try {
            EnanoDTORemoto resultado = webClientBuilder.build()
                .get()
                .uri("http://forja/api/v1/enanos/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToMono(EnanoDTORemoto.class)
                .block();

            if (resultado != null) {
                return resultado;
            }
            else {
                enanoRecuperado.setId(0);
            }

        } catch (Exception e) {
            System.out.println("El sistema con el que se esta intentando comunicar, esta caido");
            return null;
        }
        return null;
    }
}
