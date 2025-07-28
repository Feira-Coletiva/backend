package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.PedidoInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.PedidoOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Participante;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Pedido;
import br.edu.ifsc.sistemafeiracoletiva.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para pedido.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Service
public class PedidoService {
//    @Autowired
//    private PedidoRepository repository;
//
//    @Autowired
//    private ParticipanteService participanteService;
//
//    /**
//     * Lista todos os pedidos, convertendo as entidades para DTOs antes de devolver.
//     */
//    public List<PedidoOutputDTO> listar() {
//        return repository.findAll()
//                .stream()
//                .map(this::toOutputDTO)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Busca um pedido por ID e devolve um Optional<SacolaOutputDTO>.
//     */
//    public Optional<PedidoOutputDTO> buscarPorId(int id) {
//        return repository.findById(id)
//                .map(this::toOutputDTO);
//    }
//
//    /**
//     * Busca uma pedido por ID e devolve um entidade de pedido.
//     */
//    public Pedido buscarEntidadePorId(int id) {
//        return repository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Sacola não encontrado"));
//    }
//
//    /**
//     * Salva um nova pedido ou atualiza um Participante existente.
//     * Retorna o DTO da entidade salva.
//     */
//    public PedidoOutputDTO salvar(PedidoInputDTO dto, Integer id) {
//        Participante participante = participanteService.buscarEntidadePorId(dto.getIdParticipante());
//        Pedido pedido = toEntity(dto);
//        pedido.setParticipante(participante);
//        if (id != null) {
//            pedido.setId(id); // Atualização
//        }
//        Pedido salvo = repository.save(pedido);
//        return toOutputDTO(salvo);
//    }
//
//    /**
//     * Salva uma lista de Sacolas (ex: cadastro em lote).
//     */
//    public List<PedidoOutputDTO> salvarTodos(List<PedidoInputDTO> dtos) {
//        List<Pedido> pedidos = dtos.stream()
//                .map(this::toEntity)
//                .collect(Collectors.toList());
//
//        return repository.saveAll(pedidos)
//                .stream()
//                .map(this::toOutputDTO)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Remove uma Sacola pelo ID.
//     */
//    public void deletar(int id) {
//        repository.deleteById(id);
//    }
//
//    /**
//     * Verifica se uma Sacola existe por ID.
//     */
//    public boolean existePorId(int id) {
//        return repository.existsById(id);
//    }
//
//    /**
//     * Converte uma entidade Sacola para DTO de saída.
//     */
//    private PedidoOutputDTO toOutputDTO(Pedido s) {
//        return new PedidoOutputDTO(s.getId(), s.getDescricao(), s.getQtdTotalProdutos(), s.getValor());
//    }
//
//    /**
//     * Converte do DTO de entrada para entidade.
//     */
//    private Pedido toEntity(PedidoInputDTO dto) {
//        Pedido s = new Pedido();
//        s.setValor(dto.getValor());
//        s.setValor(dto.getValor());
//        s.setQtdTotalProdutos(dto.getQtdProduto());
//        Participante p = participanteService.buscarEntidadePorId(dto.getIdParticipante());
//        s.setParticipante(p);
//        return s;
//    }
//
//    /**
//     * Converte do DTO de entrada para entidade passando o participante referente
//     */
//    public Pedido toEntity(PedidoInputDTO dto, Participante Participante) {
//        Pedido s = new Pedido();
//        s.setValor(dto.getValor());
//        s.setValor(dto.getValor());
//        s.setQtdTotalProdutos(dto.getQtdProduto());
//        s.setParticipante(Participante); // <-- seta a Participante no Sacola
//        return s;
//    }
}
