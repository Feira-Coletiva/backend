package br.edu.ifsc.sistemafeiracoletiva.repository;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Oferta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório JPA para a entidade Oferta.
 * Herda métodos prontos para CRUD.
 */
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {
    @EntityGraph(attributePaths = {"produtos", "vendedor"})
    List<Oferta> findAll();
    // Busca todas as ofertas onde status_disponibilidade é TRUE
    List<Oferta> findByStatusDisponibilidadeTrue();
}
