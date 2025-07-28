package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.*;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.*;
import br.edu.ifsc.sistemafeiracoletiva.repository.PublicacaoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Publicacao.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Slf4j
@Service
public class PublicacaoService {
    @Autowired
    private PublicacaoRepository repository;
    @Autowired
    private OfertaService ofertaService;
    @Autowired
    private LocalDeRetiradaService localDeRetiradaService;
    @Autowired
    private ClienteService clienteService; // Para converter Cliente em DTO

    /**
     * Lista todas as publicações, convertendo as entidades para DTOs antes de devolver.
     * Retorna o DTO sem os detalhes dos participantes.
     */
    public List<PublicacaoOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO) // Usa o DTO sem participantes
                .collect(Collectors.toList());
    }

    /**
     * Lista as publicações referentes a um vendedor específico.
     * Retorna o DTO sem os detalhes dos participantes.
     *
     * @param vendedorId O ID do vendedor.
     * @return Uma lista de DTOs de publicações.
     */
    public List<PublicacaoOutputDTO> listarPorVendedor(Integer vendedorId) {
        return repository.findByOfertaVendedorId(vendedorId)
                .stream()
                .map(this::toOutputDTO) // Usa o DTO sem participantes
                .collect(Collectors.toList());
    }

    /**
     * Busca uma publicação por ID e devolve um Optional<PublicacaoOutputDTO>.
     * Retorna o DTO sem os detalhes dos participantes.
     */
    public Optional<PublicacaoOutputDTO> buscarPorId(int id) {
        return repository.findById(id)
                .map(this::toOutputDTO); // Usa o DTO sem participantes
    }

    /**
     * Busca uma publicação por ID e devolve a entidade de publicação.
     */
    public Publicacao buscarEntidadePorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrado"));
    }

    /**
     * ✅ NOVO: Busca uma publicação por ID e devolve um Optional<PublicacaoDetalhesOutputDTO>,
     * incluindo a lista de participantes e seus pedidos.
     * @param id ID da publicação.
     * @return Optional de PublicacaoDetalhesOutputDTO.
     */
    @Transactional // Necessário para carregar lazy relations (participantes, pedidos)
    public Optional<PublicacaoDetalhesOutputDTO> buscarPorIdComParticipantes(int id) {
        return repository.findById(id).map(this::toPublicacaoDetalhesOutputDTO);
    }

    /**
     * Salva uma nova publicação ou atualiza uma existente.
     * Retorna o DTO da entidade salva (sem detalhes de participantes).
     */
    @Transactional
    public PublicacaoOutputDTO salvar(PublicacaoInputDTO dto, Integer id) {
        Oferta oferta = ofertaService.buscarEntidadePorId(dto.getIdOferta());

        if (id == null) { // Lógica de validação e atualização do status da oferta para POST (criação)
            if (!oferta.getStatusDisponibilidade()) {
                throw new IllegalArgumentException("A oferta selecionada não está disponível para uma nova publicação.");
            }
            oferta.setStatusDisponibilidade(Boolean.FALSE);
            ofertaService.salvarEntidade(oferta); // Atualiza o status da oferta no banco
        }

        Publicacao publicacao = toEntity(dto);
        if (id != null) {
            publicacao.setId(id); // Para atualização
            // Para atualizações, você pode carregar a entidade existente e copiar os dados.
            // Para Publicacao, o construtor já define a etapa, não vem do DTO.
            Publicacao existingPublicacao = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Publicação não encontrada para atualização."));
            publicacao.setEtapa(existingPublicacao.getEtapa()); // Mantém a etapa existente
        } else {
            // Para criação, a etapa é definida no construtor da Publicacao
            // Ou, se o DTO tiver campo etapa, use-o: p.setEtapa(Etapa.valueOf(dto.getEtapa().toUpperCase()));
        }

        Publicacao salvo = repository.save(publicacao);
        return toOutputDTO(salvo); // Retorna o DTO sem participantes
    }

    /**
     * Salva uma lista de publicações (ex: cadastro em lote).
     */
    @Transactional
    public List<PublicacaoOutputDTO> salvarTodos(List<PublicacaoInputDTO> dtos) {
        List<Publicacao> publicacoes = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(publicacoes)
                .stream()
                .map(this::toOutputDTO) // Retorna o DTO sem participantes
                .collect(Collectors.toList());
    }

    /**
     * Remove uma publicacao pelo ID.
     */
    public void deletar(int id) {
        Publicacao publicacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada para exclusão."));

        // Se a oferta foi marcada como indisponível ao publicar,
        // considere marcá-la como disponível novamente ao deletar a publicação,
        // ou criar uma lógica específica de gerencia de disponibilidade.
        Oferta oferta = publicacao.getOferta();
        // Exemplo: oferta.setStatusDisponibilidade(Boolean.TRUE);
        // ofertaService.salvarEntidade(oferta);

        repository.deleteById(id);
    }

    /**
     * Verifica se uma publicacao existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade Publicacao para DTO de saída padrão (sem participantes).
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
     * ✅ NOVO: Converte uma entidade Publicacao para PublicacaoDetalhesOutputDTO (com participantes).
     */
    private PublicacaoDetalhesOutputDTO toPublicacaoDetalhesOutputDTO(Publicacao p) {
        LocalDeRetiradaOutputDTO dtoLocalDeRetirada = new LocalDeRetiradaOutputDTO(
                p.getLocalDeRetirada().getId(),
                p.getLocalDeRetirada().getNome(),
                p.getLocalDeRetirada().getCep()
        );

        OfertaOutputDTO dtoOferta = ofertaService.toOutputDTO(p.getOferta());

        // Converte a lista de participantes e seus pedidos
        List<ParticipanteResumoPublicacaoOutputDTO> participantesDTO = p.getParticipantes().stream()
                .map(this::toParticipanteResumoPublicacaoOutputDTO)
                .collect(Collectors.toList());

        return new PublicacaoDetalhesOutputDTO(
                p.getId(),
                p.getDtFinalExposicao(),
                p.getDtFinalPagamento(),
                p.getEtapa().name(),
                dtoLocalDeRetirada,
                dtoOferta,
                participantesDTO // ✅ Adicionado
        );
    }

    /**
     * ✅ NOVO: Converte uma entidade Participante para DTO de resumo para PublicacaoDetalhesOutputDTO.
     */
    private ParticipanteResumoPublicacaoOutputDTO toParticipanteResumoPublicacaoOutputDTO(Participante participante) {
        // Reutiliza ClienteOutputDTO
        ClienteOutputDTO clienteOutputDTO = new ClienteOutputDTO(
                participante.getCliente().getId(),
                participante.getCliente().getNome(),
                participante.getCliente().getEmail(),
                participante.getCliente().getTelefone()
        );

        // Converte a lista de pedidos do participante
        List<PedidoOutputDTO> pedidosOutputDTO = participante.getPedidos().stream()
                .map(this::toPedidoOutputDTO) // Reutiliza o método de conversão de Pedido
                .collect(Collectors.toList());

        return new ParticipanteResumoPublicacaoOutputDTO(
                participante.getId(),
                participante.getValorTotal(),
                participante.getQtdTotalProdutos(),
                participante.getStatusPago(),
                participante.getDataParticipacao(),
                clienteOutputDTO,
                pedidosOutputDTO
        );
    }

    /**
     * ✅ NOVO: Converte uma entidade Pedido para DTO de saída. (Pode ser copiado do ParticipanteService ou de um Mapper comum)
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

    /**
     * Converte do DTO de entrada para entidade.
     */
    private Publicacao toEntity(PublicacaoInputDTO dto) {
        // Ao criar uma Publicacao, a etapa é definida no construtor ou por padrão,
        // não necessariamente vem do DTO de entrada (a menos que você queira permitir isso).
        // A entidade Publicacao tem um construtor que define a etapa como EXPOSICAO.
        LocalDeRetirada lr = localDeRetiradaService.buscarEntidadePorId(dto.getIdLocalDeRetirada());
        Oferta o = ofertaService.buscarEntidadePorId(dto.getIdOferta());

        // Usando o construtor que define a etapa inicial
        return new Publicacao(dto.getDtFinalExposicao(), dto.getDtFinalPagamento(), lr, o);
    }
}