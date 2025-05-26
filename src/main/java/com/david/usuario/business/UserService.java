package com.david.usuario.business;

import com.david.usuario.business.converter.UserConverter;
import com.david.usuario.business.dto.UserDTO;
import com.david.usuario.infrastructure.entity.User;
import com.david.usuario.infrastructure.exceptions.ConflictException;
import com.david.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.david.usuario.infrastructure.repository.UserRepository;
import com.david.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserDTO createUser(UserDTO userDTO) {
        emailExists(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userConverter.toUser(userDTO);
        return userConverter.toUserDto(userRepository.save(user));
    }

    public void emailExists(String email) {
        try {
            boolean exists = verifiedEmail(email);
            if (exists) {
                throw new ConflictException("Email já cadastrado!" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado!" + e.getCause());
        }
    }

    public boolean verifiedEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado!"));
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public UserDTO updateUserData(String token, UserDTO userDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        userDTO.setPassword(userDTO.getPassword() != null ? passwordEncoder.encode(userDTO.getPassword()) : null);
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado!"));
        User newUser = userConverter.updateUser(userDTO, user);
        return userConverter.toUserDto(userRepository.save(newUser));
    }
}
