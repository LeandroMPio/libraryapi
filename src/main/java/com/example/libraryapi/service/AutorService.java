package com.example.libraryapi.service;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;

    public AutorService(AutorRepository autorRepository, AutorValidator validator) {
        this.autorRepository = autorRepository;
        this.validator = validator;
    }

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null)
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja cadastrado na base");
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(UUID id) {
        autorRepository.deleteById(id);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);

        if (nome != null) return autorRepository.findByNome(nome);

        if (nacionalidade != null) return autorRepository.findByNacionalidade(nacionalidade);

        return autorRepository.findAll();
    }
}
