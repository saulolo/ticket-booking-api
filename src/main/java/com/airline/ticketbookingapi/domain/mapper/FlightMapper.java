package com.airline.ticketbookingapi.domain.mapper;

import com.airline.ticketbookingapi.domain.dto.response.FlightResponseDTO;
import com.airline.ticketbookingapi.domain.entity.Flight;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Clase utilitaria para el mapeo entre la entidad Flight y los DTOs.
 */
@Component
public final class FlightMapper {

    public FlightMapper() {
    }

    /**
     * Convierte una entidad Flight a un DTO de respuesta FlightResponseDTO.
     * @param flight La entidad Flight a convertir.
     * @return El DTO de respuesta mapeado.
     */
    public static FlightResponseDTO toFlightResponseDTO(Flight flight) {
        return FlightResponseDTO.builder()
                .idFlight(flight.getIdFlight())
                .flightNumber(flight.getFlightNumber())
                .origin(flight.getOrigin())
                .destination(flight.getDestination())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .build();
    }

    /**
     * Convierte una lista de entidades Flight a una lista de DTOs de respuesta FlightResponseDTO.
     * @param flights La lista de entidades Flight a convertir.
     * @return Una lista de DTOs de respuesta mapeados.
     */
    public static List<FlightResponseDTO> toFlightResponseDTOList(List<Flight> flights) {
        return flights.stream()
                .map(FlightMapper::toFlightResponseDTO)
                .toList();
    }
}
