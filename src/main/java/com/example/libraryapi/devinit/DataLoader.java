package com.example.libraryapi.devinit;

import com.example.libraryapi.model.*;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.LivroRepository;
import com.example.libraryapi.service.ClientService;
import com.example.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final UsuarioService usuarioService;
    private final ClientService clientService;

    @Override
    public void run(String... args) throws Exception {
        Autor autor1 = new Autor();
        autor1.setNome("Maria");
        autor1.setDataNascimento(LocalDate.of(1960, 01, 01));
        autor1.setNacionalidade("Brasileira");

        Autor autor2 = new Autor();
        autor2.setNome("Josefina");
        autor2.setDataNascimento(LocalDate.of(1970, 01, 01));
        autor2.setNacionalidade("Americana");

        autorRepository.saveAll(Arrays.asList(autor1, autor2));

        Livro livro1 = new Livro();
        livro1.setIsbn("978-1585424337");
        livro1.setTitulo("As viagens");
        livro1.setDataPublicacao(LocalDate.of(1990, 01, 01));
        livro1.setGenero(GeneroLivro.FICCAO);
        livro1.setPreco(BigDecimal.valueOf(100.00));
        livro1.setAutor(autor1);

        Livro livro2 = new Livro();
        livro2.setIsbn("978-2123456803");
        livro2.setTitulo("As viagens");
        livro2.setDataPublicacao(LocalDate.of(1965, 01, 01));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setPreco(BigDecimal.valueOf(150.00));
        livro2.setAutor(autor2);

        livroRepository.saveAll(Arrays.asList(livro1, livro2));

        Usuario usuario = new Usuario();
        usuario.setLogin("gerente");
        usuario.setEmail("gerente@libraryapi.com");
        usuario.setSenha("123");
        usuario.setRoles(List.of("GERENTE"));

        usuarioService.salvar(usuario);

        Client client = new Client();
        client.setClientId("meu-client");
        client.setClientSecret("client-secret");
        client.setRedirectURI("http://localhost:8080/authorized");
        client.setScope("GERENTE");

        clientService.salvar(client);

    }
}
