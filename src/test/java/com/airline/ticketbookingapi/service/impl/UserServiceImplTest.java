package com.airline.ticketbookingapi.service.impl;

import com.airline.ticketbookingapi.domain.entity.User;
import com.airline.ticketbookingapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;


    /**
     * Prueba que el método {@code findAllUsers} devuelva una lista de usuarios cuando existen registros en la base de datos.
     * <p>
     * Este test verifica que el servicio llama al método {@code findAll} del repositorio y retorna la lista esperada de entidades de usuario.
     */
    @Test
    void testFindAllUsers() {
        //Given
        User user1 = User.builder()
                .idUser(1L)
                .name("Test name 1")
                .description("Test description 1")
                .createdDate(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .idUser(2L)
                .name("Test name 2")
                .description("Test description 2")
                .createdDate(LocalDateTime.now())
                .build();
        List<User> users = Arrays.asList(user1, user2);

        //Cuando se llame:
        when(userRepository.findAll()).thenReturn(users);

        //When
        List<User> foundUsers = userService.findAllUsers();

        //Then
        assertEquals(users, foundUsers);
        assertEquals(2, foundUsers.size());
        assertEquals("Test name 1", foundUsers.get(0).getName());
        assertEquals("Test name 2", foundUsers.get(1).getName());

    }

    /**
     * Prueba que el método {@code findUserById} retorne un objeto {@code Optional<User>} con el usuario esperado, si existe.
     * <p>
     * El test simula que el repositorio encuentra un usuario con el ID especificado y valida que el servicio lo devuelva correctamente.
     */
    @Test
    void testFindUserByIdExist() {
        // Given: Prepara un usuario de prueba y configura el mock.
        Long userId = 1L;
        User user = User.builder()
                .idUser(userId)
                .name("Test name")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When: Ejecuta el métod a probar.
        Optional<User> foundUser = userService.findUserById(userId);

        // Then: Verifica que el usuario se encuentre correctamente.
        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getIdUser());
    }

    /**
     * Prueba que el método {@code findUserById} retorne un objeto {@code Optional} vacío cuando el usuario no existe.
     * <p>
     * El test simula que el repositorio no encuentra un usuario con el ID especificado y valida que el servicio retorne un {@code Optional} vacío.
     */
    @Test
    void testFindUserwhenUserDoesNotExist() {
        // Given: Configura el mock para devolver un Optional vacío.
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When: Ejecuta el métod a probar.
        Optional<User> foundUser = userService.findUserById(userId);

        // Then: Verifica que el resultado sea un Optional vacío.
        assertTrue(foundUser.isEmpty());
    }

    /**
     * Prueba que el método {@code saveOrUpdateUser} guarde o actualice un usuario correctamente.
     * <p>
     * El test simula la operación de guardado en el repositorio y verifica que el objeto retornado por el servicio tenga un ID asignado.
     */
    @Test
    void testSaveOrUpdateUser() {
        // Given: Prepara un usuario para ser guardado y configura el mock.
        User userToSave = User.builder()
                .name("New user")
                .build();
        User savedUser = User.builder()
                .idUser(1L)
                .name("New user")
                .build();
        when(userRepository.save(userToSave)).thenReturn(savedUser);

        // When: Ejecuta el métod a probar.
        User result = userService.saveOrUpdateUser(userToSave);

        // Then: Verifica que el resultado no sea nulo y tenga un ID.
        assertNotNull(result);
        assertEquals(1L, result.getIdUser());
    }

    /**
     * Prueba que el método {@code deleteUserById} llame al método de eliminación del repositorio.
     * <p>
     * El test verifica que el servicio invoca el método {@code deleteById} del repositorio exactamente una vez con el ID correcto.
     */
    @Test
    void testDeleteUserById() {
        // Given: Prepara un ID para la eliminación.
        Long userId = 1L;
        // La eliminación de un objeto void no requiere una configuración de 'when'.

        // When: Ejecuta el métod a probar.
        userService.deleteUserById(userId);

        // Then: Verifica que el métod del repositorio se haya llamado exactamente una vez.
        verify(userRepository, times(1)).deleteById(userId);
    }
}