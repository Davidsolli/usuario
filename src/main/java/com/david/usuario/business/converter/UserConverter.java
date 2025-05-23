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
                .address(toAddressList(userDTO.getAddress()))
                .phone(toPhoneList(userDTO.getPhone()))
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
                .ddd(phoneDTO.getDdd())
                .number(phoneDTO.getNumber())
                .build();
    }

    public UserDTO toUserDto(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .address(toAddressListDTO(user.getAddress()))
                .phone(toPhoneListDTO(user.getPhone()))
                .build();
    }

    public List<AddressDTO> toAddressListDTO(List<Address> address) {
        return address.stream().map(this::toAddress).toList();
    }

    public List<PhoneDTO> toPhoneListDTO(List<Phone> phone) {
        return phone.stream().map(this::toPhone).toList();
    }

    public AddressDTO toAddress(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .city(address.getCity())
                .complement(address.getComplement())
                .cep(address.getCep())
                .state(address.getState())
                .build();
    }

    public PhoneDTO toPhone(Phone phone) {
        return PhoneDTO.builder()
                .ddd(phone.getDdd())
                .number(phone.getNumber())
                .build();
    }
}
