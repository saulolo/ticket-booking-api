package com.airline.ticketbookingapi.service.impl;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.dto.request.ReservationUpdateRequestDTO;
import com.airline.ticketbookingapi.domain.entity.Flight;
import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.airline.ticketbookingapi.domain.entity.Ticket;
import com.airline.ticketbookingapi.domain.entity.User;
import com.airline.ticketbookingapi.domain.mapper.ReservationMapper;
import com.airline.ticketbookingapi.repository.FlightRepository;
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
    private final FlightRepository flightRepository;


    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, TicketRepository ticketRepository, FlightRepository flightRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
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

    /**
     * Actualiza una reserva existente con los datos del DTO.
     * <p>
     * Este método busca la reserva por su ID, actualiza la descripción y, si se proporciona,
     * la fecha de salida del vuelo asociado.
     *
     * @param reservationUpdateRequestDTO DTO con los datos de la reserva a actualizar.
     * @return La entidad de la reserva actualizada.
     */
    @Override
    public Reservation updateReservation(ReservationUpdateRequestDTO reservationUpdateRequestDTO) {
        LOGGER.info("Actualizando reserva con ID: {}", reservationUpdateRequestDTO.idReservation());
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationUpdateRequestDTO.idReservation());

        if (reservationOptional.isEmpty()) {
            LOGGER.error("Reserva no encontrada con ID: {}", reservationUpdateRequestDTO.idReservation());
            // TODO: Lanzar una excepción personalizada
            return null;
        }

        Reservation existingReservation = reservationOptional.get();

        if (reservationUpdateRequestDTO.newDepartureTime() != null) {
            Flight flight = existingReservation.getTicket().getFlight();
            flight.setDepartureTime(reservationUpdateRequestDTO.newDepartureTime());
            flightRepository.save(flight);
        }

        if (reservationUpdateRequestDTO.description() != null && !reservationUpdateRequestDTO.description().isBlank()) {
            existingReservation.setDescription(reservationUpdateRequestDTO.description());
        }

        return reservationRepository.save(existingReservation);
    }

    /**
     * Cancela una reserva por su ID.
     * <p>
     * Este método busca una reserva y actualiza su estado a 'cancelada'.
     *
     * @param idReservation El ID de la reserva a cancelar.
     * @return La entidad de la reserva cancelada.
     */
    @Override
    public Reservation cancelReservation(Long idReservation) {
        LOGGER.info("Cancelando reserva con ID: {}", idReservation);

        Optional<Reservation> reservationOptional = reservationRepository.findById(idReservation);

        if (reservationOptional.isEmpty()) {
            LOGGER.error("Reserva no encontrada con ID: {}", idReservation);
            // TODO: Lanzar una excepción personalizada
            return null;
        }
        Reservation reservationToCancel = reservationOptional.get();

        reservationToCancel.setCancelled(true);

        return reservationRepository.save(reservationToCancel);
    }

}
