package com.airline.ticketbookingapi.data;

import com.airline.ticketbookingapi.domain.entity.Flight;

import java.time.LocalDateTime;

/**
 * Clase de utilidad para crear objetos de prueba reutilizables en tests.
 */
public class DataDummy {

    /**
     * Crea un objeto Flight de prueba.
     * @return un objeto Flight con datos predefinidos.
     */
    public static Flight flight01() {
        Flight flight01 = Flight.builder()
                .flightNumber("flightNumberTest01")
                .origin("originTest01")
                .destination("destinationTest01")
                .departureTime(LocalDateTime.now())
                .arrivalTime(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        return flight01;
    }

    /**
     * Crea un segundo objeto Flight de prueba.
     * @return un objeto Flight con datos predefinidos.
     */
    public static Flight flight02() {
        Flight flight02 = Flight.builder()
                .flightNumber("flightNumberTest02")
                .origin("originTest02")
                .destination("destinationTest02")
                .departureTime(LocalDateTime.now())
                .arrivalTime(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        return flight02;
    }
}
