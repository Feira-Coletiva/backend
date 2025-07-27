package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.*;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Cliente;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Participante;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Publicacao;
import br.edu.ifsc.sistemafeiracoletiva.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Participante.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository repository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PublicacaoService publicacaoService;

    /**
     * Lista todos os participantes, convertendo as entidades para DTOs antes de devolver.
     */
    public List<ParticipanteOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um participante por ID e devolve um Optional<participanteOutputDTO>.
     */
    public Optional<ParticipanteOutputDTO> buscarPorId(int id) {
        return repository.findById(id)
                .map(this::toOutputDTO);
    }

    /**
     * Busca uma participante por ID e devolve um entidade de participante.
     */
    public Participante buscarEntidadePorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
    }

    /**
     * Salva um nova participante ou atualiza um participante existente.
     * Retorna o DTO da entidade salva.
     */
    public ParticipanteOutputDTO salvar(ParticipanteInputDTO dto, Integer id) {
        Participante participante = toEntity(dto);
        if (id != null) {
            participante.setId(id); // Atualização
        }
        Participante salvo = repository.save(participante);
        return toOutputDTO(salvo);
    }

    /**
     * Salva uma lista de participantes (ex: cadastro em lote).
     */
    public List<ParticipanteOutputDTO> salvarTodos(List<ParticipanteInputDTO> dtos) {
        List<Participante> participantes = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(participantes)
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Remove uma participante pelo ID.
     */
    public void deletar(int id) {

        repository.deleteById(id);
    }

    /**
     * Verifica se uma participante existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade participante para DTO de saída.
     */
    private ParticipanteOutputDTO toOutputDTO(Participante p) {
        return new ParticipanteOutputDTO(p.getId(), p.getValorTotal(), p.getQtdTotalProdutos(), p.getStatusPago());
    }


    /**
     * Converte do DTO de entrada para entidade.
     */
    private Participante toEntity(ParticipanteInputDTO dto) {
        Participante p = new Participante();
        p.setValorTotal(dto.getValorTotal());
        p.setQtdTotalProdutos(dto.getQtdTotalProdutos());
        p.setStatusPago(dto.getStatusPago());
        Cliente c = clienteService.buscarEntidadePorId(dto.getIdCliente());
        p.setCliente(c);
        Publicacao pb = publicacaoService.buscarEntidadePorId(dto.getIdPublicacao());
        p.setPublicacao(pb);
        return p;
    }
}
