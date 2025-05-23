package com.david.usuario.business;

import com.david.usuario.business.converter.UserConverter;
import com.david.usuario.business.dto.UserDTO;
import com.david.usuario.infrastructure.entity.User;
import com.david.usuario.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDTO createUser(UserDTO userDTO) {
        User user = userConverter.toUser(userDTO);
        return userConverter.toUserDto(userRepository.save(user));
    }
}
