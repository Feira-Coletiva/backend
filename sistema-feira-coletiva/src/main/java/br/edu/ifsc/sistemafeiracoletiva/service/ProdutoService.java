package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.*;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.*;
import br.edu.ifsc.sistemafeiracoletiva.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Produto.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Slf4j
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private OfertaService ofertaService;

    /**
     * Lista todos os produtos, convertendo as entidades para DTOs antes de devolver.
     */
    public List<ProdutoOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um Produto por ID e devolve um Optional<ProdutoOutputDTO>.
     */
    public Optional<ProdutoOutputDTO> buscarPorId(int id) {
        return repository.findById(id)
                .map(this::toOutputDTO);
    }

    /**
     * Salva um nova produto ou atualiza um oferta existente.
     * Retorna o DTO da entidade salva.
     */
    public ProdutoOutputDTO salvar(ProdutoInputDTO dto, Integer id) {
        Oferta oferta = ofertaService.buscarEntidadePorId(dto.getIdOferta());
        Produto produto = toEntity(dto);
        produto.setOferta(oferta);
        if (id != null) {
            produto.setId(id); // Atualização
        }
        Produto salvo = repository.save(produto);
        return toOutputDTO(salvo);
    }

    /**
     * Salva uma lista de produtos (ex: cadastro em lote).
     */
    public List<ProdutoOutputDTO> salvarTodos(List<ProdutoInputDTO> dtos) {
        List<Produto> produtos = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(produtos)
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Remove uma produto pelo ID.
     */
    public void deletar(int id) {
        repository.deleteById(id);
    }

    /**
     * Verifica se uma produto existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade Produto para DTO de saída.
     */
    private ProdutoOutputDTO toOutputDTO(Produto p) {
        CategoriaOutputDTO dto = new CategoriaOutputDTO(p.getCategoria().getId(), p.getCategoria().getNome());
        return new ProdutoOutputDTO(p.getId(), p.getNome(), dto, p.getUnidadeMedida().toString(), p.getMedida(), p.getPreco(), p.getQtdEstoque());
    }

    /**
     * Converte do DTO de entrada para entidade.
     */
    private Produto toEntity(ProdutoInputDTO dto) {
        Produto p = new Produto();
        p.setNome(dto.getNome());
        Categoria c = categoriaService.buscarEntidadePorId(dto.getIdCategoria());
        p.setCategoria(c);
        // converter a String para o Enum Medida na hora de setar (Medida.valueOf())
        p.setUnidadeMedida(UnidadeDeMedida.valueOf(dto.getUnidadeMedida().toUpperCase())); // Normalizar para maiúsculo ao converter (toUpperCase())
        p.setMedida(dto.getMedida());
        p.setPreco(dto.getPreco());
        p.setQtdEstoque(dto.getQtdEstoque());
        Oferta o = ofertaService.buscarEntidadePorId(dto.getIdOferta());
        p.setOferta(o);
        return p;
    }

    /**
     * Test
     */
    public Produto toEntity(ProdutoInputDTO dto, Oferta oferta) {
        Produto p = new Produto();
        p.setNome(dto.getNome());
        Categoria c = categoriaService.buscarEntidadePorId(dto.getIdCategoria());
        p.setCategoria(c);
        p.setUnidadeMedida(UnidadeDeMedida.valueOf(dto.getUnidadeMedida().toUpperCase()));
        p.setMedida(dto.getMedida());
        p.setPreco(dto.getPreco());
        p.setQtdEstoque(dto.getQtdEstoque());
        p.setOferta(oferta); // <-- seta a oferta no produto
        return p;
    }
}
