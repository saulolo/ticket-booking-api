package com.airline.ticketbookingapi.controller;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.ReservationResponseDTO;
import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.airline.ticketbookingapi.domain.mapper.ReservationMapper;
import com.airline.ticketbookingapi.service.interfaces.IReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservationService reservationService;

    @MockBean
    private ReservationMapper reservationMapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateReservation_shouldReturnCreatedStatusAndCorrectBody_whenRequestIsSuccessful() throws Exception {
        // 1. Datos de prueba simulados
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L, "Descripción de la reserva");
        LocalDateTime createdDate = LocalDateTime.parse("21/09/2025 22:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        Reservation createdReservation = new Reservation(1L, "RES-1234", "Descripción de la reserva", createdDate, false, null, null);

        // 2. Definición del comportamiento de los Mocks
        when(reservationService.createOrUpdateReservation(any(ReservationRequestDTO.class))).thenReturn(createdReservation);

        // 3. Ejecutar la solicitud simulada y validar el resultado
        mockMvc.perform(post("/api/v1/reservations/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated()) // Espera un estado HTTP 201 (Created)
                .andExpect(jsonPath("$.idReservation").value(1L)) // Valida el ID en la respuesta JSON
                .andExpect(jsonPath("$.reservationCode").value("RES-1234")) // Valida el código de reserva
                .andExpect(jsonPath("$.description").value("Descripción de la reserva")); // Valida la descripción
    }
}