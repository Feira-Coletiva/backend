package br.edu.ifsc.sistemafeiracoletiva.repository;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {

    /**
     * Busca todos os participantes relacionados a um cliente específico.
     * @param clienteId ID do cliente.
     * @return Lista de Participante.
     */
    List<Participante> findByClienteId(Integer clienteId);

    /**
     * Busca um participante por ID, carregando os pedidos associados.
     * Útil se você precisar de todos os detalhes em uma única consulta.
     * @param id ID do participante.
     * @return Optional de Participante.
     */
    Optional<Participante> findById(Integer id); // Já existe no JpaRepository, mas é bom documentar

    /**
     * Verifica se já existe uma participação de um cliente em uma publicação específica.
     * @param clienteId ID do cliente.
     * @param publicacaoId ID da publicação.
     * @return True se existir, false caso contrário.
     */
    boolean existsByClienteIdAndPublicacaoId(Integer clienteId, Integer publicacaoId);
}

