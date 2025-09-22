package com.airline.ticketbookingapi.domain.mapper;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.ReservationResponseDTO;
import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.airline.ticketbookingapi.domain.entity.Ticket;
import com.airline.ticketbookingapi.domain.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Clase utilitaria para el mapeo entre la entidad Reservation y los DTOs.
 */
@Component
public final class ReservationMapper {

    public ReservationMapper() {
    }


    /**
     * Convierte una entidad Reservation a un DTO de respuesta.
     *
     * @param reservation La entidad Reservation a convertir.
     * @return El DTO de respuesta mapeado.
     */
    public static ReservationResponseDTO toReservationResponseDTO(Reservation reservation) {
        return ReservationResponseDTO.builder()
                .idReservation(reservation.getIdReservation())
                .reservationCode(reservation.getReservationCode())
                .description(reservation.getDescription())
                .createdDate(reservation.getCreatedDate())
                .build();
    }

    /**
     * Convierte un DTO de solicitud de reserva a una entidad Reservation.
     *
     * @param reservationRequestDTO El DTO de solicitud.
     * @param user       La entidad del usuario.
     * @param ticket     La entidad del tiquete.
     * @return La entidad Reservation mapeada.
     */
    public static Reservation toReservationEntity(ReservationRequestDTO reservationRequestDTO, User user, Ticket ticket) {
        return Reservation.builder()
                .reservationCode(generateReservationCode())
                .description(reservationRequestDTO.description())
                .createdDate(LocalDateTime.now())
                .user(user)
                .ticket(ticket)
                .build();
    }

    /**
     * Genera un código de reserva único.
     * @return El código de reserva generado.
     */
    private static String generateReservationCode() {
        return "RES-" + LocalDateTime.now().getNano();
    }

}
