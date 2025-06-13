package com.juanjooriveroo.jwtauthservice.service;

import com.juanjooriveroo.jwtauthservice.dto.ChangePasswordRequest;
import com.juanjooriveroo.jwtauthservice.dto.DeleteUserRequest;
import com.juanjooriveroo.jwtauthservice.dto.UpdateIdentifiersRequest;
import com.juanjooriveroo.jwtauthservice.exception.InvalidCredentialsException;
import com.juanjooriveroo.jwtauthservice.exception.UserAlreadyExistsException;
import com.juanjooriveroo.jwtauthservice.model.User;
import com.juanjooriveroo.jwtauthservice.repository.UserRepository;
import com.juanjooriveroo.jwtauthservice.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional
    @Override
    public boolean updateIdentifiers(UpdateIdentifiersRequest request, String token) {
        logger.info("Actualizando el/los indentificador/es...");
        User user = jwtUtil.getUserFromToken(token);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Password incorrecto");
            throw new InvalidCredentialsException("Invalid password");
        }

        if (request.getIdentifier1() != null && !request.getIdentifier1().isBlank()) {
            if (!request.getIdentifier1().equals(user.getIdentifier1())
                    && userRepository.existsByIdentifier(request.getIdentifier1())) {
                logger.warn("Identificador1 ya en uso");
                throw new UserAlreadyExistsException("Identifier1 already in use");
            }
            logger.info("Actualizando identificador1...");
            user.setIdentifier1(request.getIdentifier1());
        }

        if (request.getIdentifier2() != null && !request.getIdentifier2().isBlank()) {
            if (!request.getIdentifier2().equals(user.getIdentifier2())
                    && userRepository.existsByIdentifier(request.getIdentifier2())) {
                logger.warn("Identificador2 ya en uso");
                throw new UserAlreadyExistsException("Identifier2 already in use");
            }
            logger.info("Actualizando identificador2...");
            user.setIdentifier2(request.getIdentifier2());
        }

        User savedUser = userRepository.save(user);
        if (savedUser == null) {
            logger.warn("No se ha podido actualizar el usuario");
            return false;
        }

        if (Objects.equals(savedUser.getId(), user.getId())){
            logger.info("Usuario actualizado con éxito");
            return true;
        } else {
            logger.warn("No se ha podido actualizar el usuario");
            return false;
        }
    }

    @Transactional
    @Override
    public boolean changePassword(ChangePasswordRequest request, String token) {
        logger.info("Cambiando la contraseña...");
        User user = jwtUtil.getUserFromToken(token);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            logger.warn("Password incorrecto");
            throw new InvalidCredentialsException("Invalid current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        if (Objects.equals(userRepository.save(user).getId(), user.getId())){
            logger.info("Contraseña actualizada con éxito");
            return true;
        } else {
            logger.warn("No se ha podido actualizar la contraseña");
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteUser(DeleteUserRequest request, String token) {
        logger.info("Eliminando el usuario...");
        User user = jwtUtil.getUserFromToken(token);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Password incorrecto");
            throw new InvalidCredentialsException("Invalid password");
        }

        userRepository.deleteById(user.getId());
        if (userRepository.findById(user.getId()).isEmpty()){
            logger.info("Se ha eliminado el usuario con éxito");
            return true;
        } else {
            logger.warn("No se ha podido eliminar el usuario");
            return false;
        }
    }
}