package com.david.usuario.business;

import com.david.usuario.business.dto.ViaCepDTO;
import com.david.usuario.infrastructure.clients.ViaCepClient;
import com.david.usuario.infrastructure.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepDTO findAddressData(String cep) {
        return viaCepClient.findAddressData(cepProcess(cep));
    }

    private String cepProcess(String cep) {

        String newCep = cep
                .replace(" ", "")
                .replace("-", "");

        if (!newCep.matches("\\d+") || !Objects.equals(newCep.length(), 8)) {
            throw new IllegalArgumentException("O cep contém caracteres inválidos, por favor verificar");
        }

        return newCep;
    }
}
