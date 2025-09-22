package com.airline.ticketbookingapi.controller;

import com.airline.ticketbookingapi.domain.dto.request.UserRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.UserResponseDTO;
import com.airline.ticketbookingapi.domain.entity.User;
import com.airline.ticketbookingapi.domain.mapper.UserMapper;
import com.airline.ticketbookingapi.service.interfaces.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private IUserService userService;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @MockBean
    private UserMapper userMapper;


    @Test
    void testCreateUser() throws Exception {
        // Given: Prepara los datos de prueba y define el comportamiento de los mocks.
        UserRequestDTO requestDTO = UserRequestDTO.builder()
                .name("Test name")
                .description("Test description")
                .build();

        User newUser = User.builder()
                .idUser(1L)
                .name("Test name")
                .description("Test description")
                .createdDate(LocalDateTime.now())
                .build();

        UserResponseDTO responseDTO = UserResponseDTO.builder()
                .idUser(1L)
                .name("Test name")
                .description("Test description")
                .createdDate(LocalDateTime.now())
                .build();

        // When: Define el comportamiento del mock estático para simular la lógica de negocio.
        try (MockedStatic<UserMapper> mockedStatic = mockStatic(UserMapper.class)) {

            mockedStatic.when(() -> UserMapper.toUserEntity(any(UserRequestDTO.class))).thenReturn(newUser);
            mockedStatic.when(() -> UserMapper.toUserResponseDTO(any(User.class))).thenReturn(responseDTO);

            when(userService.saveOrUpdateUser(any(User.class))).thenReturn(newUser);

            // Ejecuta la solicitud simulada al endpoint.
            mockMvc.perform(post("/api/v1/users/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDTO)))

                    // Then: Valida el resultado de la solicitud.
                    .andExpect(status().isCreated()) // Espera un estado HTTP 201 (Created)
                    // Aquí es donde necesitas cambiar la ruta del JSON:
                    .andExpect(jsonPath("$.data.idUser").value(1L))
                    .andExpect(jsonPath("$.data.name").value("Test name"))
                    .andExpect(jsonPath("$.data.description").value("Test description"))
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.message").value("Usuario creado exitosamente."));
        }
    }
}