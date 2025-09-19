package com.airline.ticketbookingapi.repository;

import com.airline.ticketbookingapi.domain.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
