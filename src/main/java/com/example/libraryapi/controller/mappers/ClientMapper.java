package com.example.libraryapi.controller.mappers;

import com.example.libraryapi.controller.dto.CadastroClientDTO;
import com.example.libraryapi.controller.dto.ClientDTO;
import com.example.libraryapi.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(CadastroClientDTO dto);

    ClientDTO toDTO(Client client);
}
