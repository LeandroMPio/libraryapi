package com.example.libraryapi.repository.specs;

import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root,
                query,
                criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root,
                query,
                criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        return (root, query, criteriaBuilder) ->
                // and select extract(year from data_publicacao) from livro;
                // and select year(data_publicacao) from livro;
                // and select to_char(data_publicacao, 'YYYY') from livro;
                criteriaBuilder.equal(
                        criteriaBuilder.function("year", Integer.class, root.get("dataPublicacao")), anoPublicacao);
        // utiliza em postgres
//                        criteriaBuilder.function("to_char", String.class, root.get("dataPublicacao"),
//                                criteriaBuilder.literal("YYYY")), anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, criteriaBuilder) -> {
            // desta forma sempre será feito o inner join
//            return criteriaBuilder.like(criteriaBuilder.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");

            // desta forma da para controlar o tipo de join
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return criteriaBuilder.like(criteriaBuilder.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }

}
