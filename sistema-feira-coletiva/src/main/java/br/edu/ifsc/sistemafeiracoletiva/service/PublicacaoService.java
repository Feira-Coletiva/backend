package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.*;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Etapa;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.LocalDeRetirada;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Oferta;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Publicacao;
import br.edu.ifsc.sistemafeiracoletiva.repository.PublicacaoRepository;
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
     * Lista todos as publicação, convertendo as entidades para DTOs antes de devolver.
     */
    public List<PublicacaoOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um publicacao por ID e devolve um Optional<PublicacaoOutputDTO>.
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
                .orElseThrow(() -> new RuntimeException("Publicações não encontrado"));
    }

    /**
     * Salva um nova publicacao ou atualiza um oferta existente.
     * Retorna o DTO da entidade salva.
     */
    public PublicacaoOutputDTO salvar(PublicacaoInputDTO dto, Integer id) {
        Publicacao publicacao = toEntity(dto);
        if (id != null) {
            publicacao.setId(id); // Atualização
        }
        Publicacao salvo = repository.save(publicacao);
        return toOutputDTO(salvo);
    }

    /**
     * Salva uma lista de publicacoes (ex: cadastro em lote).
     */
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
     * Remove uma publicacao pelo ID.
     */
    public void deletar(int id) {
        repository.deleteById(id);
    }

    /**
     * Verifica se uma publicacao existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade Publicacao para DTO de saída.
     */
    private PublicacaoOutputDTO toOutputDTO(Publicacao p) {
        LocalDeRetiradaOutputDTO dtoLocalDeRetirada = new LocalDeRetiradaOutputDTO(p.getLocalDeRetirada().getId(), p.getLocalDeRetirada().getNome(), p.getLocalDeRetirada().getCep());
        return new PublicacaoOutputDTO(p.getId(), p.getDtFinalExposicao(), p.getDtFinalPagamento(), p.getEtapa().name(), dtoLocalDeRetirada);
    }

    /**
     * Converte do DTO de entrada para entidade.
     */
    private Publicacao toEntity(PublicacaoInputDTO dto) {
        Publicacao p = new Publicacao();
        p.setDtFinalExposicao(dto.getDtFinalExposicao());
        p.setDtFinalPagamento(dto.getDtFinalPagamento());
        p.setEtapa(Etapa.valueOf(dto.getEtapa().toUpperCase()));
        LocalDeRetirada lr = localDeRetiradaService.buscarEntidadePorId(dto.getIdLocalDeRetirada());
        Oferta o = ofertaService.buscarEntidadePorId(dto.getIdOferta());
        p.setLocalDeRetirada(lr);
        p.setOferta(o);
        return p;
    }

}
