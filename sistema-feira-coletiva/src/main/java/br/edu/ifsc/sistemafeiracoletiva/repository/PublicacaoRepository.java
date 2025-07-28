package br.edu.ifsc.sistemafeiracoletiva.repository;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Integer> {
    /**
     * ✅ NOVO: Busca todas as publicações criadas por um vendedor específico,
     * utilizando a relação entre Publicacao -> Oferta -> Vendedor.
     */
    List<Publicacao> findByOfertaVendedorId(Integer vendedorId);
}
