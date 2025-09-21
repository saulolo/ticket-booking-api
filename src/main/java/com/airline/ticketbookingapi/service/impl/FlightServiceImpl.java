package com.airline.ticketbookingapi.service.impl;

import com.airline.ticketbookingapi.domain.entity.Flight;
import com.airline.ticketbookingapi.repository.FlightRepository;
import com.airline.ticketbookingapi.service.interfaces.IFlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements IFlightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceImpl.class);
    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Flight> findFlightsByOriginAndDestination(String origin, String destination) {
        LOGGER.info("Buscando vuelos de: {} a {}. " , origin, destination);
        return flightRepository.findByOriginAndDestination(origin, destination);
    }
}
