package com.example.libraryapi.controller;

import com.example.libraryapi.GenericController;
import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.controller.mappers.AutorMapper;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = autorMapper.toEntity(dto);
        autorService.salvar(autor);

        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);

        return autorService.obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) return ResponseEntity.notFound().build();

        autorService.deletar(autorOptional.get().getId());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> autores = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> autoresDTO = autores
                .stream()
                .map(autorMapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(autoresDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO autorDto) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) return ResponseEntity.notFound().build();

        Autor autor = autorOptional.get();
        autor.setNome(autorDto.nome());
        autor.setDataNascimento(autorDto.dataNascimento());
        autor.setNacionalidade(autorDto.nacionalidade());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}
