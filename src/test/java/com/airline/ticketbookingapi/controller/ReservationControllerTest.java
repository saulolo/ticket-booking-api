package com.airline.ticketbookingapi.controller;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.dto.request.ReservationUpdateRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.ReservationResponseDTO;
import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.airline.ticketbookingapi.domain.mapper.ReservationMapper;
import com.airline.ticketbookingapi.service.interfaces.IReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private IReservationService reservationService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }


    /**
     * Este caso de prueba simula la creación exitosa de una reserva.
     * <p>
     * Verifica que el controlador procese correctamente una solicitud POST,
     * interactúe con el servicio mockeado y devuelva una respuesta HTTP 201 (Created)
     * con el cuerpo esperado.
     *
     * @throws Exception si ocurre un error durante la simulación de la solicitud HTTP.
     */
    @Test
    void testCreateReservation() throws Exception {
        // Given (Arrange): Datos de prueba simulados
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L, "Descripción de la reserva");
        LocalDateTime createdDate = LocalDateTime.parse("21/09/2025 22:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        Reservation createdReservation = new Reservation(1L, "RES-1234", "Descripción de la reserva", createdDate, false, null, null);
        ReservationResponseDTO responseDTO = ReservationResponseDTO.builder()
                .idReservation(1L)
                .reservationCode("RES-1234")
                .description("Descripción de la reserva")
                .build();

        // 2. Mockear el servicio
        when(reservationService.createOrUpdateReservation(any(ReservationRequestDTO.class))).thenReturn(createdReservation);

        // 3. Mockear el método estático
        try (MockedStatic<ReservationMapper> mockedMapper = mockStatic(ReservationMapper.class)) {
            mockedMapper.when(() -> ReservationMapper.toReservationResponseDTO(any(Reservation.class)))
                    .thenReturn(responseDTO);

            // When (Act): Ejecutar la solicitud simulada y validar el resultado
            mockMvc.perform(post("/api/v1/reservations/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDTO)))

                    // Then (Assert): Validar el resultado
                    .andExpect(status().isCreated()) // Espera un estado HTTP 201 (Created)
                    .andExpect(jsonPath("$.data.idReservation").value(1L))
                    .andExpect(jsonPath("$.data.reservationCode").value("RES-1234"))
                    .andExpect(jsonPath("$.data.description").value("Descripción de la reserva"))
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.message").value("Reserva creada exitosamente."));
        }
    }


    /**
     * Prueba la funcionalidad del endpoint de actualización de reserva.
     * <p>
     * Este test verifica que una solicitud PUT para actualizar una reserva sea
     * manejada correctamente, simulando un escenario de éxito donde el servicio
     * devuelve una reserva actualizada. Valida que el controlador retorne un
     * estado HTTP 200 (OK) y el cuerpo de respuesta esperado.
     *
     * @throws Exception si la simulación de la solicitud falla.
     */
    @SneakyThrows
    @Test
    void testUpdateReservation() {
        // Given: Configurar los datos de prueba y los mocks
        Long reservationId = 1L;
        String description = "Nueva descripción de la reserva";
        LocalDateTime newDepartureTime = LocalDateTime.of(2025, 9, 23, 10, 0, 0);
        ReservationUpdateRequestDTO requestDTO = new ReservationUpdateRequestDTO(reservationId, newDepartureTime, description);
        Reservation updatedReservation = new Reservation(reservationId, "RES-1234", "Nueva descripción de la reserva",
                LocalDateTime.of(2025, 9, 23, 10, 0, 0), false, null, null);
        ReservationResponseDTO responseDTO = ReservationResponseDTO.builder()
                .idReservation(reservationId)
                .reservationCode("RES-1234")
                .description("Nueva descripción de la reserva")
                .build();

        when(reservationService.updateReservation(any(ReservationUpdateRequestDTO.class))).thenReturn(updatedReservation);

        try (MockedStatic<ReservationMapper> mockedMapper = mockStatic(ReservationMapper.class)) {
            mockedMapper.when(() -> ReservationMapper.toReservationResponseDTO(any(Reservation.class)))
                    .thenReturn(responseDTO);

            // When: Ejecutar la solicitud HTTP de actualización
            mockMvc.perform(put("/api/v1/reservations/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.idReservation").value(1L))
                    .andExpect(jsonPath("$.data.reservationCode").value("RES-1234"))
                    .andExpect(jsonPath("$.data.description").value("Nueva descripción de la reserva"))
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.message").value("Reserva actualizada exitosamente."));
        }
    }

    /**
     * Prueba la funcionalidad del endpoint de cancelación de reserva.
     * <p>
     * Este test verifica que el controlador procese correctamente una solicitud DELETE
     * para cancelar una reserva existente. Simula un escenario de éxito donde el servicio
     * devuelve una reserva marcada como cancelada y valida que el controlador
     * retorne un estado HTTP 200 (OK) con el cuerpo de respuesta esperado.
     *
     * @throws Exception si la simulación de la solicitud HTTP falla.
     */
    @Test
    void testCancelReservation() throws Exception {
        // Given: Configurar los datos de prueba y los mocks
        Long reservationId = 1L;
        Reservation canceledReservation = new Reservation(reservationId, "RES-1234", "Descripción de la reserva",
                LocalDateTime.of(2025, 9, 23, 10, 0, 0), true, null, null); // true para indicar que está cancelada
        ReservationResponseDTO responseDTO = ReservationResponseDTO.builder()
                .idReservation(reservationId)
                .reservationCode("RES-1234")
                .description("Descripción de la reserva")
                .build();

        // Cuando se llama a reservationService.cancelReservation, retorna el objeto de reserva cancelado.
        when(reservationService.cancelReservation(any(Long.class))).thenReturn(canceledReservation);

        // Y cuando se llama al mapper, retorna el DTO de respuesta esperado.
        try (MockedStatic<ReservationMapper> mockedMapper = mockStatic(ReservationMapper.class)) {
            mockedMapper.when(() -> ReservationMapper.toReservationResponseDTO(any(Reservation.class)))
                    .thenReturn(responseDTO);

            // When: Ejecutar la solicitud HTTP de eliminación
            mockMvc.perform(delete("/api/v1/reservations/delete/{idReservation}", reservationId))

                    // Then: Validar el resultado
                    .andExpect(status().isOk()) // Espera un estado HTTP 200 (OK)
                    .andExpect(jsonPath("$.data.idReservation").value(1L))
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.message").value("Reserva cancelada exitosamente."));
        }
    }
}