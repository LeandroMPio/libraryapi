package com.example.libraryapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor")
@Getter
@Setter
@ToString(exclude = {"livros"})
@EntityListeners(AuditingEntityListener.class)
public class Autor {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

    @JsonIgnore
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
              //(mappedBy = "autor", cascade = CascadeType.ALL)
                // utilizando o CascadeType.ALL quando salvar um autor será salvo a lista de livros
                // e também quando deletar será deletado todos os livros pertencentes a este autor
    private List<Livro> livros;
}
