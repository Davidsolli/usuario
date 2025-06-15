package com.david.usuario.business.converter;

import com.david.usuario.business.dto.AddressDTO;
import com.david.usuario.business.dto.PhoneDTO;
import com.david.usuario.business.dto.UserDTO;
import com.david.usuario.infrastructure.entity.Address;
import com.david.usuario.infrastructure.entity.Phone;
import com.david.usuario.infrastructure.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    public User toUser(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress() != null ? toAddressList(userDTO.getAddress()) : null)
                .phone(userDTO.getPhone() != null ? toPhoneList(userDTO.getPhone()) : null)
                .build();
    }

    public List<Address> toAddressList(List<AddressDTO> addressDTOS) {
        return addressDTOS.stream().map(this::toAddress).toList();
    }

    public List<Phone> toPhoneList(List<PhoneDTO> phoneDTOS) {
        return phoneDTOS.stream().map(this::toPhone).toList();
    }

    public Address toAddress(AddressDTO addressDTO) {
        return Address.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .city(addressDTO.getCity())
                .complement(addressDTO.getComplement())
                .cep(addressDTO.getCep())
                .state(addressDTO.getState())
                .build();
    }

    public Phone toPhone(PhoneDTO phoneDTO) {
        return Phone.builder()
                .id(phoneDTO.getId())
                .ddd(phoneDTO.getDdd())
                .number(phoneDTO.getNumber())
                .build();
    }

    public UserDTO toUserDto(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress() != null ? toAddressListDTO(user.getAddress()) : null)
                .phone(user.getPhone() != null ? toPhoneListDTO(user.getPhone()) : null)
                .build();
    }

    public List<AddressDTO> toAddressListDTO(List<Address> address) {
        return address.stream().map(this::toAddressDTO).toList();
    }

    public List<PhoneDTO> toPhoneListDTO(List<Phone> phone) {
        return phone.stream().map(this::toPhoneDTO).toList();
    }

    public AddressDTO toAddressDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .city(address.getCity())
                .complement(address.getComplement())
                .cep(address.getCep())
                .state(address.getState())
                .build();
    }

    public PhoneDTO toPhoneDTO(Phone phone) {
        return PhoneDTO.builder()
                .id(phone.getId())
                .ddd(phone.getDdd())
                .number(phone.getNumber())
                .build();
    }

    public User updateUser(UserDTO userDTO, User user) {
        return User.builder()
                .email(userDTO.getEmail() != null ? userDTO.getEmail() : user.getEmail())
                .name(userDTO.getName() != null ? userDTO.getName() : user.getName())
                .password(userDTO.getPassword() != null ? userDTO.getPassword() : user.getPassword())
                .id(user.getId())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }

    public Address updateAddress(AddressDTO addressDTO, Address address) {
        return Address.builder()
                .id(address.getId())
                .street(addressDTO.getStreet() != null ? addressDTO.getStreet() : address.getStreet())
                .number(addressDTO.getNumber() != null ? addressDTO.getNumber() : address.getNumber())
                .city(addressDTO.getCity() != null ? addressDTO.getCity() : address.getCity())
                .cep(addressDTO.getCep() != null ? addressDTO.getCep() : address.getCep())
                .complement(addressDTO.getComplement() != null ? addressDTO.getComplement() : address.getComplement())
                .state(addressDTO.getState() != null ? addressDTO.getState() : address.getState())
                .build();
    }

    public Phone updatePhone(PhoneDTO phoneDTO, Phone phone) {
        return Phone.builder()
                .id(phone.getId())
                .number(phoneDTO.getNumber() != null ? phoneDTO.getNumber() : phone.getNumber())
                .ddd(phoneDTO.getDdd() != null ? phoneDTO.getDdd() : phone.getDdd())
                .build();
    }

    public Address toNewAddress(AddressDTO addressDTO, Long userId) {
        return Address.builder()
                .street(addressDTO.getStreet())
                .city(addressDTO.getCity())
                .cep(addressDTO.getCep())
                .complement(addressDTO.getComplement())
                .state(addressDTO.getState())
                .number(addressDTO.getNumber())
                .userId(userId)
                .build();
    }

    public Phone toNewPhone(PhoneDTO phoneDTO, Long userId) {
        return Phone.builder()
                .number(phoneDTO.getNumber())
                .ddd(phoneDTO.getDdd())
                .userId(userId)
                .build();
    }
}