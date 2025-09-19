package com.airline.ticketbookingapi.service.impl;

import com.airline.ticketbookingapi.domain.entity.User;
import com.airline.ticketbookingapi.repository.UserRepository;
import com.airline.ticketbookingapi.service.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User saveOrUpdateUser(User user) {
        LOGGER.info("Creado o actualizado un Usuario. ✓ ");
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        LOGGER.info("Usuario elimnado con exito. ✓ ");

    }
}
