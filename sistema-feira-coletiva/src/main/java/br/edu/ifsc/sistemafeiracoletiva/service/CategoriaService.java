package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.CategoriaInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.CategoriaOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Categoria;
import br.edu.ifsc.sistemafeiracoletiva.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Categoria.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    /**
     * Lista todos os categorias, convertendo as entidades para DTOs antes de devolver.
     */
    public List<CategoriaOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um categoria por ID e devolve um Optional<CategoriaOutputDTO>.
     */
    public Optional<CategoriaOutputDTO> buscarPorId(int id) {
        return repository.findById(id)
                .map(this::toOutputDTO);
    }

    /**
     * Busca uma categoria por ID e devolve um entidade de categoria.
     */
    public Categoria buscarEntidadePorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado"));
    }

    /**
     * Salva um novo categoria ou atualiza um categoria existente.
     * Retorna o DTO da entidade salva.
     */
    public CategoriaOutputDTO salvar(CategoriaInputDTO dto, Integer id) {
        Categoria categoria = toEntity(dto);
        if (id != null) {
            categoria.setId(id);
        }
        Categoria salvo = repository.save(categoria);
        return toOutputDTO(salvo);
    }

    /**
     * Salva uma lista de categoria (ex: cadastro em lote).
     */
    public List<CategoriaOutputDTO> salvarTodos(List<CategoriaInputDTO> dtos) {
        List<Categoria> categorias = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(categorias)
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Remove um categoria pelo ID.
     */
    public void deletar(int id) {
        repository.deleteById(id);
    }

    /**
     * Verifica se um categoria existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade Categoria para DTO de saída.
     */
    private CategoriaOutputDTO toOutputDTO(Categoria c) {
        return new CategoriaOutputDTO(c.getId(), c.getNome());
    }

    /**
     * Converte do DTO de entrada para entidade.
     */
    private Categoria toEntity(CategoriaInputDTO dto) {
        Categoria c = new Categoria();
        c.setNome(dto.getNome());
        return c;
    }
}
