package com.airline.ticketbookingapi.service.interfaces;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.dto.request.ReservationUpdateRequestDTO;
import com.airline.ticketbookingapi.domain.entity.Reservation;

public interface IReservationService {

    /**
     * Crea una nueva reserva para un tiquete y un usuario.
     * @param reservationRequestDTO El DTO que contiene el ID del usuario y del tiquete.
     * @return La entidad de la reserva creada.
     */
    Reservation createOrUpdateReservation(ReservationRequestDTO reservationRequestDTO);

    /**
     * Actualiza una reserva existente.
     * @param reservationUpdateRequestDTO El DTO con los datos a actualizar de la reserva.
     * @return La entidad de la reserva actualizada.
     */
    Reservation updateReservation(ReservationUpdateRequestDTO reservationUpdateRequestDTO);
}
