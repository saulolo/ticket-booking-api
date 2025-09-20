package com.airline.ticketbookingapi.domain.mapper;

import com.airline.ticketbookingapi.domain.dto.request.UserRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.UserResponseDTO;
import com.airline.ticketbookingapi.domain.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Clase utilitaria para el mapeo entre las entidades de usuario y los objetos de transferencia de datos (DTO).
 */
@Component
public final class UserMapper {

    public UserMapper() {
    }

    /**
     * Convierte una entidad User a un DTO de respuesta UserResponseDTO.
     * @param user La entidad User a convertir.
     * @return El DTO de respuesta mapeado.
     */
    public static UserResponseDTO toUserResponseDTO(User user) {

        return UserResponseDTO.builder()
                .idUser(user.getIdUser())
                .name(user.getName())
                .description(user.getDescription())
                .createdDate(user.getCreatedDate())
                .reservations(user.getReservations())
                .build();
    }

    /**
     * Convierte un DTO de solicitud UserRequestDTO a una entidad User.
     * @param userRequestDTO El DTO de solicitud a convertir.
     * @return La entidad User mapeada.
     */
    public static User toUserEntity(UserRequestDTO userRequestDTO) {
        return User.builder()
                .name(userRequestDTO.name())
                .description(userRequestDTO.description())
                .createdDate(LocalDateTime.now())
                .build();
    }
}
