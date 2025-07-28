package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.*;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.*;
import br.edu.ifsc.sistemafeiracoletiva.repository.ParticipanteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Participante.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Slf4j
@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository repository;
    @Autowired
    private ClienteService clienteService; // Para buscar o Cliente
    @Autowired
    private PublicacaoService publicacaoService; // Para buscar a Publicacao
    @Autowired
    private OfertaService ofertaService; // Para acessar produtos via oferta

    /**
     * Lista todas as participações, convertendo as entidades para DTOs.
     * @return Lista de ParticipanteOutputDTO.
     */
    public List<ParticipanteOutputDTO> listar() {
        return repository.findAll().stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma participação por ID e devolve um Optional<ParticipanteOutputDTO>.
     * @param id ID da participação.
     * @return Optional de ParticipanteOutputDTO.
     */
    public Optional<ParticipanteOutputDTO> buscarPorId(int id) {
        return repository.findById(id).map(this::toOutputDTO);
    }

    /**
     * Lista todas as participações de um cliente específico.
     * @param clienteId ID do cliente.
     * @return Lista de ParticipanteOutputDTO.
     */
    public List<ParticipanteOutputDTO> listarPorCliente(Integer clienteId) {
        return repository.findByClienteId(clienteId).stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Salva uma nova participação com seus pedidos.
     *
     * @param dto DTO de entrada com informações do participante e seus pedidos.
     * @return ParticipanteOutputDTO salvo.
     * @throws IllegalArgumentException se o cliente ou publicação não existirem,
     * ou se a oferta não estiver disponível, ou se o produto não for da oferta da publicação,
     * ou se a quantidade pedida for maior que o estoque disponível (neste caso, apenas informamos, não decrementamos).
     */
    @Transactional
    public ParticipanteOutputDTO criarParticipacao(ParticipanteInputDTO dto) {
        Cliente cliente = clienteService.buscarEntidadePorId(dto.getIdCliente());
        Publicacao publicacao = publicacaoService.buscarEntidadePorId(dto.getIdPublicacao());
        Oferta ofertaAssociada = publicacao.getOferta(); // A oferta relacionada à publicação

        // Opcional: Impedir que o mesmo cliente participe múltiplas vezes da mesma publicação
        if (repository.existsByClienteIdAndPublicacaoId(cliente.getId(), publicacao.getId())) {
            throw new IllegalArgumentException("Este cliente já possui uma participação registrada para esta publicação.");
        }

        // Criar a entidade Participante
        Participante participante = new Participante(cliente, publicacao);

        // Processar cada pedido
        for (PedidoInputDTO pedidoDto : dto.getPedidos()) {
            // Buscar o produto dentro da oferta da publicação
            // Iteramos sobre os produtos da oferta para encontrar o correto
            Produto produto = ofertaAssociada.getProdutos().stream()
                    .filter(p -> p.getId().equals(pedidoDto.getIdProduto()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto com ID " + pedidoDto.getIdProduto() + " não encontrado na oferta da publicação."
                    ));

            // ✅ Lógica de verificação de estoque (não decrementa, apenas informa)
            if (pedidoDto.getQtdProdutos() > produto.getQtdEstoque()) {
                log.warn("Tentativa de pedir mais do que o estoque disponível para o produto {}. Pedido: {}, Estoque: {}",
                        produto.getNome(), pedidoDto.getQtdProdutos(), produto.getQtdEstoque());
                // Poderíamos lançar uma exceção ou ajustar a quantidade aqui se o requisito mudasse
                throw new IllegalArgumentException(
                        "Quantidade solicitada para o produto '" + produto.getNome() + "' excede o estoque disponível. Estoque: " + produto.getQtdEstoque()
                );
            }

            // Criar a entidade Pedido
            Pedido pedido = new Pedido(
                    produto,
                    pedidoDto.getQtdProdutos(),
                    produto.getPreco(), // Copia o preço unitário do produto no momento do pedido
                    participante // O participante ao qual este pedido pertence
            );
            participante.addPedido(pedido); // Adiciona o pedido e atualiza os totais do participante
        }

        Participante salvo = repository.save(participante);
        return toOutputDTO(salvo);
    }

    /**
     * Converte uma entidade Participante para DTO de saída.
     * @param p Entidade Participante.
     * @return ParticipanteOutputDTO.
     */
    private ParticipanteOutputDTO toOutputDTO(Participante p) {
        ClienteOutputDTO clienteOutputDTO = new ClienteOutputDTO(
                p.getCliente().getId(),
                p.getCliente().getNome(),
                p.getCliente().getEmail(),
                p.getCliente().getTelefone()
        );

        PublicacaoResumoOutputDTO publicacaoResumoDTO = new PublicacaoResumoOutputDTO(
                p.getPublicacao().getId(),
                p.getPublicacao().getOferta().getTitulo(), // Título da oferta como título da publicação
                p.getPublicacao().getDtFinalExposicao(),
                p.getPublicacao().getLocalDeRetirada().getNome(),
                p.getPublicacao().getEtapa().name()
        );

        List<PedidoOutputDTO> pedidosOutputDTO = p.getPedidos().stream()
                .map(this::toPedidoOutputDTO)
                .collect(Collectors.toList());

        return new ParticipanteOutputDTO(
                p.getId(),
                p.getValorTotal(),
                p.getQtdTotalProdutos(),
                p.getStatusPago(),
                p.getDataParticipacao(),
                clienteOutputDTO,
                publicacaoResumoDTO,
                pedidosOutputDTO
        );
    }

    /**
     * Converte uma entidade Pedido para DTO de saída.
     * @param pedido Entidade Pedido.
     * @return PedidoOutputDTO.
     */
    private PedidoOutputDTO toPedidoOutputDTO(Pedido pedido) {
        ProdutoSimplesOutputDTO produtoSimplesDTO = new ProdutoSimplesOutputDTO(
                pedido.getProduto().getId(),
                pedido.getProduto().getNome(),
                pedido.getProduto().getCategoria().getNome(),
                pedido.getProduto().getUnidadeMedida(),
                pedido.getProduto().getMedida()
        );

        return new PedidoOutputDTO(
                pedido.getId(),
                produtoSimplesDTO,
                pedido.getQtdProdutos(),
                pedido.getPrecoUnitarioNoPedido(),
                pedido.getValorTotalItem()
        );
    }
}
