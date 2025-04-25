package com.example.libraryapi.controller;

import com.example.libraryapi.GenericController;
import com.example.libraryapi.controller.dto.UsuarioDTO;
import com.example.libraryapi.controller.mappers.UsuarioMapper;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController implements GenericController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuarioService.salvar(usuario);
        
        URI location = gerarHeaderLocation(usuario.getId());

        return ResponseEntity.created(location).build();
    }
}
