package com.airline.ticketbookingapi.controller;

import com.airline.ticketbookingapi.domain.dto.response.ApiResponseDTO;
import com.airline.ticketbookingapi.domain.dto.response.FlightResponseDTO;
import com.airline.ticketbookingapi.domain.mapper.FlightMapper;
import com.airline.ticketbookingapi.service.interfaces.IFlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    private final IFlightService flightService;

    public FlightController(IFlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * Busca vuelos disponibles por origen y destino.
     *
     * @param origin El punto de partida del vuelo.
     * @param destination El punto de llegada del vuelo.
     * @return ResponseEntity con la estructura estandarizada de la API, un estado HTTP 200 OK y una lista de vuelos encontrados.
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<FlightResponseDTO>>> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination) {

        List<FlightResponseDTO> flights = FlightMapper.toFlightResponseDTOList(
                flightService.findFlightsByOriginAndDestination(origin, destination)
        );

        if (flights.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponseDTO<>(null, "success", "No se encontraron vuelos"),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                new ApiResponseDTO<>(flights, "success", "Vuelos encontrados con éxito"),
                HttpStatus.OK
        );
    }
}