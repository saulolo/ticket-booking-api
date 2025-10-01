package com.airline.ticketbookingapi.controller;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.dto.request.ReservationUpdateRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.ApiResponseDTO;
import com.airline.ticketbookingapi.domain.dto.response.ReservationResponseDTO;
import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.airline.ticketbookingapi.domain.mapper.ReservationMapper;
import com.airline.ticketbookingapi.service.interfaces.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final IReservationService reservationService;


    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }


    /**
     * Crea una nueva reserva para un usuario y un tiquete.
     *
     * @param reservationRequestDTO El DTO de solicitud con los datos de la reserva.
     * @return ResponseEntity con la estructura estandarizada de la API, un estado HTTP 201 Created y los datos de la reserva creada.
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<ReservationResponseDTO>> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        Reservation newReservation = reservationService.createOrUpdateReservation(reservationRequestDTO);
        ReservationResponseDTO reservationResponseDTO = ReservationMapper.toReservationResponseDTO(newReservation);

        return new ResponseEntity<>(
                new ApiResponseDTO<>(reservationResponseDTO, "success", "Reserva creada exitosamente."),
                HttpStatus.CREATED
        );
    }


    /**
     * Actualiza una reserva existente.
     *
     * @param requestDTO El DTO con los datos de la reserva a actualizar.
     * @return ResponseEntity con la estructura estandarizada de la API, un estado HTTP 200 OK y los datos de la reserva actualizada.
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponseDTO<ReservationResponseDTO>> updateReservation(@RequestBody ReservationUpdateRequestDTO requestDTO) {
        Reservation updatedReservation = reservationService.updateReservation(requestDTO);
        ReservationResponseDTO responseDTO = ReservationMapper.toReservationResponseDTO(updatedReservation);

        return new ResponseEntity<>(
                new ApiResponseDTO<>(responseDTO, "success", "Reserva actualizada exitosamente."),
                HttpStatus.OK
        );
    }


    /**
     * Cancela una reserva por su ID.
     *
     * @param idReservation El ID de la reserva a cancelar.
     * @return ResponseEntity con la estructura estandarizada de la API, un estado HTTP 200 OK y los datos de la reserva cancelada.
     */
    @DeleteMapping("/delete/{idReservation}")
    public ResponseEntity<ApiResponseDTO<ReservationResponseDTO>> cancelReservation(@PathVariable Long idReservation) {
        Reservation canceledReservation = reservationService.cancelReservation(idReservation);
        ReservationResponseDTO responseDTO = ReservationMapper.toReservationResponseDTO(canceledReservation);

        return new ResponseEntity<>(
                new ApiResponseDTO<>(responseDTO, "success", "Reserva cancelada exitosamente."),
                HttpStatus.OK
        );
    }
}
