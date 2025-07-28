package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.*;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Etapa;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.LocalDeRetirada;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Oferta;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Publicacao;
import br.edu.ifsc.sistemafeiracoletiva.repository.PublicacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Publicacao.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Service
public class PublicacaoService {
    @Autowired
    private PublicacaoRepository repository;
    @Autowired
    private OfertaService ofertaService;
    @Autowired
    private LocalDeRetiradaService localDeRetiradaService;

    /**
     * Lista todas as publicações, convertendo as entidades para DTOs antes de devolver.
     */
    public List<PublicacaoOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista as publicações referentes a um vendedor específico.
     *
     * @param vendedorId O ID do vendedor.
     * @return Uma lista de DTOs de publicações.
     */
    public List<PublicacaoOutputDTO> listarPorVendedor(Integer vendedorId) {
        return repository.findByOfertaVendedorId(vendedorId)
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma publicação por ID e devolve um Optional<PublicacaoOutputDTO>.
     */
    public Optional<PublicacaoOutputDTO> buscarPorId(int id) {
        return repository.findById(id)
                .map(this::toOutputDTO);
    }

    /**
     * Busca uma publicacao por ID e devolve um entidade de publicacao.
     */
    public Publicacao buscarEntidadePorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrado"));
    }

    /**
     * ✅ RE-ADICIONADO: Salva uma nova publicação ou atualiza uma existente.
     * Retorna o DTO da entidade salva.
     */
    @Transactional
    public PublicacaoOutputDTO salvar(PublicacaoInputDTO dto, Integer id) {
        // Lógica de validação e atualização do status da oferta para POST e PUT
        Oferta oferta = ofertaService.buscarEntidadePorId(dto.getIdOferta());

        // Apenas para criação (POST): verifica disponibilidade e altera status
        if (id == null) {
            if (!oferta.getStatusDisponibilidade()) {
                throw new IllegalArgumentException("A oferta selecionada não está disponível para uma nova publicação.");
            }
            oferta.setStatusDisponibilidade(Boolean.FALSE);
            ofertaService.salvarEntidade(oferta);
        }

        Publicacao publicacao = toEntity(dto);
        if (id != null) {
            publicacao.setId(id); // Para atualização
        }

        Publicacao salvo = repository.save(publicacao);
        return toOutputDTO(salvo);
    }

    /**
     * ✅ RE-ADICIONADO: Salva uma lista de publicacoes (ex: cadastro em lote).
     */
    @Transactional
    public List<PublicacaoOutputDTO> salvarTodos(List<PublicacaoInputDTO> dtos) {
        List<Publicacao> publicacoes = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(publicacoes)
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * ✅ RE-ADICIONADO: Remove uma publicacao pelo ID.
     */
    public void deletar(int id) {
        repository.deleteById(id);
    }

    /**
     * ✅ RE-ADICIONADO: Verifica se uma publicacao existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade Publicacao para DTO de saída.
     * ✅ CORRIGIDO: Passa o OfertaOutputDTO que agora contém a lista de produtos.
     */
    private PublicacaoOutputDTO toOutputDTO(Publicacao p) {
        LocalDeRetiradaOutputDTO dtoLocalDeRetirada = new LocalDeRetiradaOutputDTO(
                p.getLocalDeRetirada().getId(),
                p.getLocalDeRetirada().getNome(),
                p.getLocalDeRetirada().getCep()
        );

        OfertaOutputDTO dtoOferta = ofertaService.toOutputDTO(p.getOferta());

        return new PublicacaoOutputDTO(
                p.getId(),
                p.getDtFinalExposicao(),
                p.getDtFinalPagamento(),
                p.getEtapa().name(),
                dtoLocalDeRetirada,
                dtoOferta
        );
    }

    /**
     * ✅ RE-ADICIONADO: Converte do DTO de entrada para entidade.
     */
    private Publicacao toEntity(PublicacaoInputDTO dto) {
        Publicacao p = new Publicacao();
        p.setDtFinalExposicao(dto.getDtFinalExposicao());
        p.setDtFinalPagamento(dto.getDtFinalPagamento());
        // A etapa pode vir do DTO ou ser definida como padrão
//        p.setEtapa(Etapa.valueOf(dto.getEtapa().toUpperCase()));

        LocalDeRetirada lr = localDeRetiradaService.buscarEntidadePorId(dto.getIdLocalDeRetirada());
        Oferta o = ofertaService.buscarEntidadePorId(dto.getIdOferta());

        p.setLocalDeRetirada(lr);
        p.setOferta(o);
        return p;
    }
}
