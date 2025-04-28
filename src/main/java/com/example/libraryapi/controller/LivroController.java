package com.example.libraryapi.controller;

import com.example.libraryapi.GenericController;
import com.example.libraryapi.controller.dto.CadastroLivroDTO;
import com.example.libraryapi.controller.dto.ErroResposta;
import com.example.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.example.libraryapi.controller.mappers.LivroMapper;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
@Tag(name = "Livros")
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo Livro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErroResposta.class))),
    })
    public ResponseEntity<Void> salva(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        livroService.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Obter detalhes", description = "Retorna os dados do livro pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.", content = @Content)
    })
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable String id) {
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    ResultadoPesquisaLivroDTO dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta um livro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.", content = @Content),
    })
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Pesquisa paginada de livros por parâmetros.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso."),
    })
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {

        Page<Livro> pageLivros = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<ResultadoPesquisaLivroDTO> pageLivrosDTO = pageLivros.map(livroMapper::toDTO);

        return ResponseEntity.ok(pageLivrosDTO);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualiza um livro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErroResposta.class))),
    })
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid CadastroLivroDTO dto) {
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAuxiliar = livroMapper.toEntity(dto);
                    livro.setIsbn(entidadeAuxiliar.getIsbn());
                    livro.setPreco(entidadeAuxiliar.getPreco());
                    livro.setGenero(entidadeAuxiliar.getGenero());
                    livro.setTitulo(entidadeAuxiliar.getTitulo());
                    livro.setAutor(entidadeAuxiliar.getAutor());

                    livroService.atualizar(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
