package com.example.libraryapi.validator;

import com.example.libraryapi.exceptions.CampoInvalidoException;
import com.example.libraryapi.exceptions.RegistroDuplicadoException;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository livroRepository;

    public void validar(Livro livro) {
        if (existeLivroComIsbn(livro)) throw new RegistroDuplicadoException("Já existe um livro com este ISBN");

        if (isPrecoObrigatorioNulo(livro)) throw new CampoInvalidoException(
                "preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatório");
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO && livro.getPreco() == null;
    }

    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        // verifica se é um novo livro se for ele está cadastrando
        if (livro.getId() == null) return livroEncontrado.isPresent();

        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
