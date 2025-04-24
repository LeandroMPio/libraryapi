package com.example.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,

        @NotBlank(message = "O campo nome é obrigatório")
        @Size(max = 100, message = "O campo nome deve ter no máximo 100 caracteres")
        String nome,

        @NotNull(message = "O campo data de nascimento é obrigatório")
        @Past(message = "O campo data de nascimento deve ser uma data no passado")
        LocalDate dataNascimento,

        @NotBlank(message = "O campo nacionalidade é obrigatório")
        @Size(max = 50, message = "O campo nacionalidade deve ter no máximo 50 caracteres")
        String nacionalidade) {
}
