package com.airline.ticketbookingapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;


@Builder
@JsonPropertyOrder({"data", "status", "message"})
public record ApiResponseDTO<T>(
        T data,
        String status,
        String message) {
}
