package com.airline.ticketbookingapi.service.interfaces;

import com.airline.ticketbookingapi.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    /**
     * Busca todos los usuarios en el sistema.
     *
     * @return Una lista de todas las entidades de usuario.
     */
    List<User> findAllUsers();

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return Un Optional que contiene el usuario si es encontrado.
     */
    Optional<User> findUserById(Long id);

    /**
     * Guarda o actualiza un usuario en la base de datos.
     *
     * @param user La entidad de usuario a guardar o actualizar.
     * @return La entidad de usuario guardada.
     */
    User saveOrUpdateUser(User user);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     */
    void deleteUserById(Long id);

}
