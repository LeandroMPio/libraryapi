package com.example.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "login é obrigatório")
        String login,

        @Email(message = "email inválido")
        @NotBlank(message = "email é obrigatório")
        String email,

        @NotBlank(message = "senha é obrigatória")
        String senha,

        List<String> roles) {
}
