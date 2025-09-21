package com.airline.ticketbookingapi.service.interfaces;

import com.airline.ticketbookingapi.domain.entity.Flight;

import java.util.List;

public interface IFlightService {


    /**
     * Busca una lista de vuelos por su origen y destino.
     * @param origin El punto de partida del vuelo.
     * @param destination El punto de llegada del vuelo.
     * @return Una lista de vuelos que coinciden con el origen y destino especificados.
     */
    List<Flight> findFlightsByOriginAndDestination(String origin, String destination);
}
