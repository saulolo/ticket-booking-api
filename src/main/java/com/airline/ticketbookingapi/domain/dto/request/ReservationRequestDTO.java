package com.airline.ticketbookingapi.domain.dto.request;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"idUser", "idTicket", "description"})
public record ReservationRequestDTO(
        long idUser,
        Long idTicket,
        String description) {
}
