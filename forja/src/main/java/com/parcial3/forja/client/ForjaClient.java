package com.parcial3.forja.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public interface ForjaClient {
    private final WebClient webClient;

    // Inyectamos el builder para construir el cliente con la URL base definida
    public ForjaClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8082")
                .build();
    }

    public List<Object> obtenerEquipamientosForjados(Integer enanoId) {
        return webClient.get()
                .uri("/api/v1/sables/buscar-por-jedi/{id}", enanoId)
                .retrieve()
                .bodyToFlux(Object.class) // Usamos bodyToFlux si esperas una lista
                .collectList()
                .block(); // .block() para mantener la sincronía en este caso
    }
}