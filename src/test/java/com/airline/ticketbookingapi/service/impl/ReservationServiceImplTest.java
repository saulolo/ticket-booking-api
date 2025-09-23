package com.airline.ticketbookingapi.service.impl;

import com.airline.ticketbookingapi.domain.dto.request.ReservationRequestDTO;
import com.airline.ticketbookingapi.domain.dto.request.ReservationUpdateRequestDTO;
import com.airline.ticketbookingapi.domain.entity.Flight;
import com.airline.ticketbookingapi.domain.entity.Reservation;
import com.airline.ticketbookingapi.domain.entity.Ticket;
import com.airline.ticketbookingapi.domain.entity.User;
import com.airline.ticketbookingapi.exception.TicketNotFoundException;
import com.airline.ticketbookingapi.exception.UserNotFoundException;
import com.airline.ticketbookingapi.repository.FlightRepository;
import com.airline.ticketbookingapi.repository.ReservationRepository;
import com.airline.ticketbookingapi.repository.TicketRepository;
import com.airline.ticketbookingapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private FlightRepository flightRepository;

    private User testUser;
    private Ticket testTicket;
    private Reservation testReservation;
    private Flight testFlight;

    @BeforeEach
    void setUp() {
        testUser = User.builder().idUser(1L).name("Test User").build();
        testTicket = Ticket.builder().idTicket(1L).seatNumber("A1").isAvailable(true).build();
        testFlight = Flight.builder().idFlight(1L).build();
        testReservation = Reservation.builder().idReservation(1L).reservationCode("RES123").user(testUser).ticket(testTicket).build();

        testTicket.setFlight(testFlight);
    }

    // --- Tests para createOrUpdateReservation ---

    /**
     * Prueba el escenario de éxito para la creación de una reserva.
     * <p>
     * Verifica que el servicio devuelva un objeto de reserva cuando el usuario y el tiquete existen.
     */
    @Test
    void createOrUpdateReservationWhenUserAndTicketExist() {
        // Given
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L, "Descrition test");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);

        // When
        Reservation result = reservationService.createOrUpdateReservation(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(testReservation.getIdReservation(), result.getIdReservation());
        verify(userRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    /**
     * Prueba el escenario de fallo cuando el usuario no existe.
     * <p>
     * Verifica que el servicio lance una {@link UserNotFoundException} y no llame a los demás repositorios.
     */
    @Test
    void createOrUpdateReservationwhenUserDoesNotExist() {
        // Given
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(99L, 1L, "Descrition test");
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> reservationService.createOrUpdateReservation(requestDTO));
        verify(userRepository, times(1)).findById(99L);
        verify(ticketRepository, never()).findById(anyLong()); // Verifica que este métod nunca se llame
    }

    /**
     * Prueba el escenario de fallo cuando el tiquete no existe.
     * <p>
     * Verifica que el servicio lance una {@link TicketNotFoundException} después de encontrar al usuario.
     */
    @Test
    void testCreateOrUpdateReservationWhenTicketDoesNotExist() {
        // Given
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 99L, "Descrition test");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TicketNotFoundException.class, () -> reservationService.createOrUpdateReservation(requestDTO));
        verify(userRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).findById(99L);
    }


    // --- Tests para updateReservation ---

    /**
     * Prueba la actualización de una reserva existente con una nueva descripción.
     * <p>
     * Este test verifica que el servicio actualice el campo de descripción y guarde la reserva modificada.
     */
    @Test
    void updateReservationWhenTicketDoesNotExist() {
        // Given
        ReservationUpdateRequestDTO updateRequestDTO = new ReservationUpdateRequestDTO(1L, LocalDateTime.now(), "Descrption test");

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);

        // When
        Reservation result = reservationService.updateReservation(updateRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals("Descrption test", result.getDescription());
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(testReservation);

    }

    /**
     * Prueba la actualización de la hora de salida de un vuelo asociada a una reserva.
     * <p>
     * Verifica que el servicio actualice el {@code departureTime} del vuelo y guarde los cambios en el repositorio.
     */
    @Test
    void testUpdateReservationWhenNewDepartureTimeIsProvided() {
        // Given
        ReservationUpdateRequestDTO updateRequestDTO = new ReservationUpdateRequestDTO(1L, LocalDateTime.now(), "Descrption test");

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        when(flightRepository.save(any(Flight.class))).thenReturn(testFlight);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);

        // When
        Reservation result = reservationService.updateReservation(updateRequestDTO);

        // Then
        assertNotNull(result);
        verify(reservationRepository, times(1)).findById(1L);
        verify(flightRepository, times(1)).save(testFlight);
        verify(reservationRepository, times(1)).save(testReservation);
    }

    // --- Tests para cancelReservation ---

    /**
     * Prueba la cancelación exitosa de una reserva existente.
     * <p>
     * Este test verifica que el servicio encuentre la reserva, cambie su estado a cancelado y la guarde.
     */
    @Test
    void cancelReservation() {
        // Given
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(testReservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);

        // When
        Reservation result = reservationService.cancelReservation(reservationId);

        // Then
        assertNotNull(result);
        assertTrue(result.isCancelled());
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, times(1)).save(testReservation);
    }
}