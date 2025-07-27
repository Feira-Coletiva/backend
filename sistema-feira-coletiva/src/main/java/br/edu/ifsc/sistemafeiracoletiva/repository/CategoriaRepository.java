package br.edu.ifsc.sistemafeiracoletiva.repository;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório JPA para a entidade Categoria.
 * Herda métodos prontos para CRUD.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByNome(String nome);
}
