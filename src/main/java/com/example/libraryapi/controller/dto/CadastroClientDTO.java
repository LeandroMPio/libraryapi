package com.example.libraryapi.controller.dto;

import java.util.UUID;

public record CadastroClientDTO(
        UUID id,
        String clientId,
        String clientSecret,
        String redirectURI,
        String scope
) {
}
