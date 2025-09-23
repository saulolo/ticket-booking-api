package com.airline.ticketbookingapi.service.impl;

import com.airline.ticketbookingapi.domain.entity.Flight;
import com.airline.ticketbookingapi.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

    @InjectMocks
    private FlightServiceImpl flightService;

    @Mock
    private FlightRepository flightRepository;


    /**
     * Clase de pruebas unitarias para {@link FlightServiceImpl}.
     * Utiliza Mockito para simular el comportamiento de {@link FlightRepository}
     * y probar la lógica de negocio de la capa de servicio de vuelos.
     */
    @Test
    void testFindFlightsByOriginAndDestination() {
        // Given (Arreglo): Prepara los datos de prueba y configura el comportamiento del mock.
        String origin = "Medellín";
        String destination = "Bogotá";

        Flight flight1 = Flight.builder()
                .idFlight(1L)
                .origin(origin)
                .destination(destination)
                .flightNumber("AV123")
                .departureTime(LocalDateTime.of(2025, 10, 26, 8, 0))
                .arrivalTime(LocalDateTime.of(2025, 10, 26, 9, 0))
                .build();

        List<Flight> flightList = Arrays.asList(flight1);

        // Cuando se llame a flightRepository.findByOriginAndDestination, devuelve la lista de prueba.
        when(flightRepository.findByOriginAndDestination(origin, destination)).thenReturn(flightList);

        // When (Acción): Ejecuta el métod que se quiere probar.
        List<Flight> foundFlights = flightService.findFlightsByOriginAndDestination(origin, destination);

        // Then (Validación): Verifica que el resultado sea el esperado.
        assertNotNull(foundFlights);
        assertEquals(1, foundFlights.size());
        assertEquals(origin, foundFlights.get(0).getOrigin());
        assertEquals(destination, foundFlights.get(0).getDestination());
    }
}