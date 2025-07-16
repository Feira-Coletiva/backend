package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.LocalDeRetiradaInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.LocalDeRetiradaOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.LocalDeRetirada;
import br.edu.ifsc.sistemafeiracoletiva.repository.LocalDeRetiradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Local De Retirada.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Service
public class    LocalDeRetiradaService {
    @Autowired
    private LocalDeRetiradaRepository repository;

    /**
     * Lista todos os locais de retirada, convertendo as entidades para DTOs antes de devolver.
     */
    public List<LocalDeRetiradaOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um local de retirada por ID e devolve um Optional<LocalDeRetiradaOutputDTO>.
     */
    public Optional<LocalDeRetiradaOutputDTO> buscarPorId(int id) {
        return repository.findById(id)
                .map(this::toOutputDTO);
    }

    /**
     * Busca uma local de retirada por ID e devolve um entidade de LocalDeRetirada.
     */
    public LocalDeRetirada buscarEntidadePorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("LocalDeRetirada não encontrado"));
    }

    /**
     * Salva um novo local de retirada ou atualiza um local de retirada existente.
     * Retorna o DTO da entidade salva.
     */
    public LocalDeRetiradaOutputDTO salvar(LocalDeRetiradaInputDTO dto, Integer id) {
        LocalDeRetirada localDeRetirada = toEntity(dto);
        if (id != null) {
            localDeRetirada.setId(id);
        }
        LocalDeRetirada salvo = repository.save(localDeRetirada);
        return toOutputDTO(salvo);
    }

    /**
     * Salva uma lista de locais de retirada (ex: cadastro em lote).
     */
    public List<LocalDeRetiradaOutputDTO> salvarTodos(List<LocalDeRetiradaInputDTO> dtos) {
        List<LocalDeRetirada> locaisDeRetirada = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(locaisDeRetirada)
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Remove um local de retirada pelo ID.
     */
    public void deletar(int id) {
        repository.deleteById(id);
    }

    /**
     * Verifica se um local de retirada existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade LocalDeRetirada para DTO de saída.
     */
    private LocalDeRetiradaOutputDTO toOutputDTO(LocalDeRetirada lr) {
        return new LocalDeRetiradaOutputDTO(lr.getId(), lr.getNome(), lr.getCep());
    }

    /**
     * Converte do DTO de entrada para entidade.
     */
    private LocalDeRetirada toEntity(LocalDeRetiradaInputDTO dto) {
        LocalDeRetirada lr = new LocalDeRetirada();
        lr.setNome(dto.getNome());
        lr.setCep(dto.getCep());
        return lr;
    }
}
