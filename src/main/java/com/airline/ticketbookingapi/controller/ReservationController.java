package com.airline.ticketbookingapi.controller;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.dto.request.ReservationUpdateRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.ReservationResponseDTO;
import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.airline.ticketbookingapi.domain.mapper.ReservationMapper;
import com.airline.ticketbookingapi.service.interfaces.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    /**
     * Actualiza una reserva existente.
     *
     * @param requestDTO DTO con los datos de la reserva a actualizar.
     * @return El DTO de respuesta de la reserva actualizada.
     */
    @PutMapping("/update")
    public ResponseEntity<ReservationResponseDTO> updateReservation(@RequestBody ReservationUpdateRequestDTO requestDTO) {
        Reservation updatedReservation = reservationService.updateReservation(requestDTO);
        ReservationResponseDTO responseDTO = ReservationMapper.toReservationResponseDTO(updatedReservation);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
