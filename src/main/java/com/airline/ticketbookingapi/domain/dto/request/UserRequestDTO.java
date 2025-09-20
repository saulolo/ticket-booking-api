package com.airline.ticketbookingapi.domain.dto.request;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

/**
 * DTO de solicitud para la creación y actualización de usuarios.
 * Contiene los campos necesarios para recibir datos del cliente de la API.
 */
@Builder
@JsonPropertyOrder({"name", "description"})
public record UserRequestDTO(
        String name,
        String description) {
}
