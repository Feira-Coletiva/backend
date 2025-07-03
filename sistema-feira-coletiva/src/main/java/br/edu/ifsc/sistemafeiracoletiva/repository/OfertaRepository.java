package br.edu.ifsc.sistemafeiracoletiva.repository;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório JPA para a entidade Oferta.
 * Herda métodos prontos para CRUD.
 */
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {
}
