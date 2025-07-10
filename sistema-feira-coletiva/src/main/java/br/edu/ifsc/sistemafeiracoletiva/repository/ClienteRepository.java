package br.edu.ifsc.sistemafeiracoletiva.repository;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório JPA para a entidade Cliente.
 * Herda métodos prontos para CRUD.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByEmail(String email);
    /*
    * Herdando JpaRepository, ela já ganha, automaticamente, métodos prontos como:
    * findAll()         Lista todos os clientes
    * findById(id)      Busca por ID
    * save(cliente)     Salva ou atualiza
    * deleteById(id)    Remove
    */

    /*
    * Consultas personalizadas:
    *   Optional<Cliente> findByEmail(String email);        buscar um cliente por e-mail
    *   List<Cliente> findByNomeContaining(String nome);    buscar por nome que contenha um texto
    *
    *   @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %:nome%")         JPQL personalizado
    *   List<Cliente> buscarPorNome(@Param("nome") String nome);
    */
}
