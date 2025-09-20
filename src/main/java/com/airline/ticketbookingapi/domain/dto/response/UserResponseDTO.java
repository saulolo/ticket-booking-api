package com.airline.ticketbookingapi.domain.dto.response;

import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta para la información del usuario.
 * Utilizado para exponer los datos del usuario en la API de manera segura.
 */
@Builder
@JsonPropertyOrder({"idUser", "name", "description", "createdDate", "reservations"})
public record UserResponseDTO(
        Long idUser,
        String name,
        String description,
        LocalDateTime createdDate,
        List<Reservation> reservations) {
}
