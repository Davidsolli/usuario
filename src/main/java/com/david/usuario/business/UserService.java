package com.david.usuario.business;

import com.david.usuario.business.converter.UserConverter;
import com.david.usuario.business.dto.AddressDTO;
import com.david.usuario.business.dto.PhoneDTO;
import com.david.usuario.business.dto.UserDTO;
import com.david.usuario.infrastructure.entity.Address;
import com.david.usuario.infrastructure.entity.Phone;
import com.david.usuario.infrastructure.entity.User;
import com.david.usuario.infrastructure.exceptions.ConflictException;
import com.david.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.david.usuario.infrastructure.repository.AddressRepository;
import com.david.usuario.infrastructure.repository.PhoneRepository;
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
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;

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

    public UserDTO findUserByEmail(String email) {
        try {
            return userConverter.toUserDto(userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado!")));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado!");
        }
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
        User UpdatedUser = userConverter.updateUser(userDTO, user);
        return userConverter.toUserDto(userRepository.save(UpdatedUser));
    }

    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address address = addressRepository
                .findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado!"));
        Address updatedAddress = userConverter.updateAddress(addressDTO, address);
        return userConverter.toAddressDTO(addressRepository.save(updatedAddress));
    }

    public PhoneDTO updatePhone(Long phoneId, PhoneDTO phoneDTO) {
        Phone phone = phoneRepository
                .findById(phoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Telefone não encontrado!"));
        Phone updatedPhone = userConverter.updatePhone(phoneDTO, phone);
        return userConverter.toPhoneDTO(phoneRepository.save(updatedPhone));
    }
}
