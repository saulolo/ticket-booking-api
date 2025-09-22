package com.airline.ticketbookingapi.domain.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO de solicitud para actualizar una reserva existente.
 * Contiene el ID de la reserva y los campos que pueden ser modificados.
 */
@Builder
@JsonPropertyOrder({"idReservation", "newDepartureTime", "description"})
public record ReservationUpdateRequestDTO(
        Long idReservation,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime newDepartureTime,
        String description) {
}
