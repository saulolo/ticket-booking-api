package com.airline.ticketbookingapi.repository;

import com.airline.ticketbookingapi.data.DataDummy;
import com.airline.ticketbookingapi.domain.entity.Flight;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private Flight flight1;
    private Flight flight2;


    @BeforeEach
    void setUp() {
        // Preparar datos para cada test
        flight1 = DataDummy.flight01();
        flight2 = DataDummy.flight02();
        flightRepository.save(flight1);
        flightRepository.save(flight2);
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        ticketRepository.deleteAll();
        flightRepository.deleteAll();
    }

    @Test
    @DisplayName("Devolver una lista de vuelos cuando se encuentran coincidencias")
    void testFindByOriginAndDestinationWhenMatchesAreFound() {
        //When
        List<Flight> foundFlights  = flightRepository.findByOriginAndDestination( "originTest01", "destinationTest01");

        //Then
        assertNotNull(foundFlights);
        assertEquals(1, foundFlights.size());
        assertEquals(flight1.getOrigin(), foundFlights.get(0).getOrigin());
        assertEquals(flight1.getDestination(), foundFlights.get(0).getDestination());
    }

    @Test
    @DisplayName("Debe devolver una lista vacía cuando no hay vuelos que coincidan")
    void testFindByOriginAndDestinationWhenNoFlightsAreFound() {
        // When (Acción)
        // Intentamos buscar vuelos con un origen y destino que no existen.
        List<Flight> foundFlights = flightRepository.findByOriginAndDestination(anyString(), anyString());

        // Then (Validación)
        assertNotNull(foundFlights);
        assertEquals(0, foundFlights.size());
    }
}