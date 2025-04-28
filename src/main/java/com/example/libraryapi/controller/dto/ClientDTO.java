package com.example.libraryapi.controller.dto;

import java.util.UUID;

public record ClientDTO(
        UUID id,
        String redirectURI,
        String scope
) {
}
