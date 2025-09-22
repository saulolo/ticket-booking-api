package com.airline.ticketbookingapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonPropertyOrder({"idReservation", "reservationCode", "description", "createdDate"})
public record ReservationResponseDTO(
        Long idReservation,
        String reservationCode,
        String description,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdDate) {
}
