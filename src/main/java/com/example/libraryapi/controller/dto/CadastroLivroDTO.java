package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "O campo ISBN é obrigatório")
        String isbn,

        @NotBlank(message = "O campo título é obrigatório")
        String titulo,

        @NotNull(message = "O campo data de publicação é obrigatório")
        @Past(message = "O campo data de publicação deve ser uma data no passado")
        LocalDate dataPublicacao,

        GeneroLivro genero,
        BigDecimal preco,

        @NotNull(message = "O campo autor é obrigatório")
        UUID idAutor) {
}
