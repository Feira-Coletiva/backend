package br.edu.ifsc.sistemafeiracoletiva.repository;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório JPA para a entidade Vendedor.
 * Herda métodos prontos para CRUD.
 */
public interface VendedorRepository  extends JpaRepository<Vendedor, Integer> {
    Optional<Vendedor> findByEmail(String email);
}
