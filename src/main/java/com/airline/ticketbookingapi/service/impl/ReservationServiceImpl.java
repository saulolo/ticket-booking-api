package com.airline.ticketbookingapi.service.impl;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.airline.ticketbookingapi.domain.entity.Ticket;
import com.airline.ticketbookingapi.domain.entity.User;
import com.airline.ticketbookingapi.domain.mapper.ReservationMapper;
import com.airline.ticketbookingapi.repository.ReservationRepository;
import com.airline.ticketbookingapi.repository.TicketRepository;
import com.airline.ticketbookingapi.repository.UserRepository;
import com.airline.ticketbookingapi.service.interfaces.IReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationServiceImpl implements IReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }


    /**
     * Crea o actualiza una reserva a partir de los datos proporcionados.
     * <p>
     * Se encarga de buscar las entidades de usuario y tiquete, mapear el DTO a la entidad
     * y guardar la nueva reserva en la base de datos.
     *
     * @param reservationRequestDTO DTO con los IDs del usuario y del tiquete para la reserva.
     * @return La entidad de la reserva guardada.
     */
    @Override
    public Reservation createOrUpdateReservation(ReservationRequestDTO reservationRequestDTO) {

        Optional<User> userOptional = userRepository.findById(reservationRequestDTO.idUser());
        Optional<Ticket> ticketOptional = ticketRepository.findById(reservationRequestDTO.idTicket());

        if (userOptional.isEmpty() || ticketOptional.isEmpty()) {
            LOGGER.error("Usuario o tiquete no encontrado. No se puede crear la reserva.");
            //TODO: Lanzar una excepción personalizada
            return null;
        }

        User user = userOptional.get();
        Ticket ticket = ticketOptional.get();

        Reservation reservation = ReservationMapper.toReservationEntity(reservationRequestDTO, user, ticket);

        return reservationRepository.save(reservation);
    }
}
