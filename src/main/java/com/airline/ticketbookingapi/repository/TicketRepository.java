package com.airline.ticketbookingapi.repository;

import com.airline.ticketbookingapi.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
