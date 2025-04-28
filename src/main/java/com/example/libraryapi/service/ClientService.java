package com.example.libraryapi.service;

import com.example.libraryapi.model.Client;
import com.example.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client salvar(Client client) {
        return clientRepository.save(client);
    }

    public Client obterPorClientID(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
