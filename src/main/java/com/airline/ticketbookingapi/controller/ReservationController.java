package com.airline.ticketbookingapi.controller;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.ReservationResponseDTO;
import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.airline.ticketbookingapi.domain.mapper.ReservationMapper;
import com.airline.ticketbookingapi.service.interfaces.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final IReservationService reservationService;


    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }


    /**
     * Crea una nueva reserva para un usuario y un tiquete.
     *
     * @param reservationRequestDTO El DTO de solicitud con los datos de la reserva.
     * @return El DTO de respuesta con la reserva creada y un estado HTTP 201 Created.
     */
    @PostMapping("/create")
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        Reservation newReservation = reservationService.createOrUpdateReservation(reservationRequestDTO);
        ReservationResponseDTO reservationResponseDTO = ReservationMapper.toReservationResponseDTO(newReservation);
        return new ResponseEntity<>(reservationResponseDTO, HttpStatus.CREATED);
    }

}
