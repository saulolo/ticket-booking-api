package com.airline.ticketbookingapi.controller;

import com.airline.ticketbookingapi.domain.dto.request.UserRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.ApiResponseDTO;
import com.airline.ticketbookingapi.domain.dto.response.UserResponseDTO;
import com.airline.ticketbookingapi.domain.entity.User;
import com.airline.ticketbookingapi.domain.mapper.UserMapper;
import com.airline.ticketbookingapi.service.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }


    /**
     * Crea un nuevo usuario.
     *
     * @param userRequestDTO El DTO con los datos del usuario a crear.
     * @return ResponseEntity con la estructura estandarizada de la API, un estado HTTP 201 Created y los datos del usuario creado.
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        User newUser = UserMapper.toUserEntity(userRequestDTO);
        User createdUser = userService.saveOrUpdateUser(newUser);
        UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(createdUser);

        return new ResponseEntity<>(
                new ApiResponseDTO<>(userResponseDTO, "success", "Usuario creado exitosamente."),
                HttpStatus.CREATED
        );
    }

    /**
     * Actualiza un usuario existente por su ID.
     *
     * @param id             El ID del usuario a actualizar.
     * @param userRequestDTO El DTO con los nuevos datos.
     * @return ResponseEntity con la estructura estandarizada de la API, un estado HTTP 200 OK y los datos del usuario actualizado. Retorna 404 Not Found si no existe.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        Optional<User> userOptional = userService.findUserById(id);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(userRequestDTO.name());
            existingUser.setDescription(userRequestDTO.description());

            User updatedUser = userService.saveOrUpdateUser(existingUser);
            UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(updatedUser);

            return new ResponseEntity<>(
                    new ApiResponseDTO<>(userResponseDTO, "success", "Usuario actualizado exitosamente."),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new ApiResponseDTO<>(null, "error", "Usuario no encontrado."),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return ResponseEntity con la estructura estandarizada de la API, un estado HTTP 200 OK y los datos del usuario. Retorna 404 Not Found si no existe.
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> findUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(user);
            return new ResponseEntity<>(
                    new ApiResponseDTO<>(userResponseDTO, "success", "Usuario encontrado exitosamente."),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponseDTO<>(null, "error", "Usuario no encontrado."),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * Busca todos los usuarios.
     *
     * @return ResponseEntity con la estructura estandarizada de la API, un estado HTTP 200 OK y una lista de usuarios. Retorna un mensaje informativo si la lista está vacía.
     */
    @GetMapping("/findAll")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> findAllUsers() {
        List<User> users = userService.findAllUsers();

        if (users.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponseDTO<>(null, "success", "No se encontraron usuarios."),
                    HttpStatus.OK
            );
        } else {
            List<UserResponseDTO> userResponseDTOS = users.stream()
                    .map(UserMapper::toUserResponseDTO)
                    .toList();
            return new ResponseEntity<>(
                    new ApiResponseDTO<>(userResponseDTOS, "success", "Usuarios encontrados exitosamente."),
                    HttpStatus.OK
            );
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     * @return ResponseEntity con la estructura estandarizada de la API, un estado HTTP 200 OK y un mensaje de éxito. Retorna 404 Not Found si no existe.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteById(@PathVariable Long id) {
        Optional<User> existingUser = userService.findUserById(id);

        if (existingUser.isPresent()) {
            userService.deleteUserById(id);
            return new ResponseEntity<>(
                    new ApiResponseDTO<>(null, "success", "Usuario eliminado exitosamente."),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponseDTO<>(null, "error", "Usuario no encontrado."),
                    HttpStatus.NOT_FOUND
            );
        }
    }

}
