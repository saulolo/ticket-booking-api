package com.airline.ticketbookingapi.controller;

import com.airline.ticketbookingapi.domain.dto.request.UserRequestDTO;
import com.airline.ticketbookingapi.domain.dto.response.UserResponseDTO;
import com.airline.ticketbookingapi.domain.entity.User;
import com.airline.ticketbookingapi.domain.mapper.UserMapper;
import com.airline.ticketbookingapi.service.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }


    /**
     * Crea un nuevo usuario.
     *
     * @param userRequestDTO El DTO con los datos del usuario a crear.
     * @return ResponseEntity con el DTO del usuario creado y un estado HTTP 201 Created.
     */
    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) throws URISyntaxException {
        User newUser = UserMapper.toUserEntity(userRequestDTO);
        User createUser = userService.saveOrUpdateUser(newUser);
        UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(createUser);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Actualiza un usuario existente por su ID.
     *
     * @param id             El ID del usuario a actualizar.
     * @param userRequestDTO El DTO con los nuevos datos.
     * @return ResponseEntity con el DTO del usuario actualizado y un estado HTTP 200 OK.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        Optional<User> userOptional = userService.findUserById(id);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(userRequestDTO.name());
            existingUser.setDescription(userRequestDTO.description());
            existingUser.setCreatedDate(LocalDateTime.now());
            User updatedUser = userService.saveOrUpdateUser(existingUser);
            UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(updatedUser);
            return ResponseEntity.ok(userResponseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return ResponseEntity con el DTO del usuario y un estado HTTP 200 OK, o 404 Not Found si no existe.
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(user);
            return ResponseEntity.ok(userResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca todos los usuarios.
     *
     * @return ResponseEntity con una lista de DTOs de usuarios y estado HTTP 200 OK, o 204 No Content si la lista está vacía.
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        List<User> users = userService.findAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<UserResponseDTO> userResponseDTOS = users.stream()
                    .map(UserMapper::toUserResponseDTO)
                    .toList();
            return ResponseEntity.ok(userResponseDTOS);
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     * @return ResponseEntity con un estado HTTP 204 No Content si se elimina, o 404 Not Found si no existe.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<User> existingUser = userService.findUserById(id);

        if (existingUser.isPresent()) {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
