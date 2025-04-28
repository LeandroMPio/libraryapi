package com.example.libraryapi.controller;

import com.example.libraryapi.GenericController;
import com.example.libraryapi.controller.dto.CadastroClientDTO;
import com.example.libraryapi.controller.dto.ClientDTO;
import com.example.libraryapi.controller.mappers.ClientMapper;
import com.example.libraryapi.model.Client;
import com.example.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController implements GenericController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientDTO> salvar(@RequestBody CadastroClientDTO dto) {
        Client novoClient = clientMapper.toEntity(dto);
        Client client = clientService.salvar(novoClient);

        URI location = gerarHeaderLocation(novoClient.getId());

        ClientDTO clientDTO = clientMapper.toDTO(client);

        return ResponseEntity.created(location).body(clientDTO);
    }
}
