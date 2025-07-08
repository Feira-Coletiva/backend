package br.edu.ifsc.sistemafeiracoletiva.service;


import br.edu.ifsc.sistemafeiracoletiva.dto.OfertaInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.OfertaOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.ResumoVendedorOfertaDTO;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Oferta;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Vendedor;
import br.edu.ifsc.sistemafeiracoletiva.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Oferta.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Service
public class OfertaService {

    @Autowired
    private OfertaRepository repository;

    @Autowired
    private VendedorService vendedorService;

    /**
     * Lista todos os ofertas, convertendo as entidades para DTOs antes de devolver.
     */
    public List<OfertaOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um Oferta por ID e devolve um Optional<OfertaOutputDTO>.
     */
    public Optional<OfertaOutputDTO> buscarPorId(int id) {
        return repository.findById(id)
                .map(this::toOutputDTO);
    }

    /**
     * Busca uma oferta por ID e devolve um entidade de oferta.
     */
    public Oferta buscarEntidadePorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta não encontrado"));
    }

    /**
     * Salva um nova oferta ou atualiza um oferta existente.
     * Retorna o DTO da entidade salva.
     */
    public OfertaOutputDTO salvar(OfertaInputDTO dto, Integer id) {
        Oferta oferta = toEntity(dto);
        if (id != null) {
            oferta.setId(id); // Atualização
        }
        Oferta salvo = repository.save(oferta);
        return toOutputDTO(salvo);
    }

    /**
     * Salva uma lista de ofertas (ex: cadastro em lote).
     */
    public List<OfertaOutputDTO> salvarTodos(List<OfertaInputDTO> dtos) {
        List<Oferta> ofertas = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(ofertas)
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Remove uma oferta pelo ID.
     */
    public void deletar(int id) {
        repository.deleteById(id);
    }

    /**
     * Verifica se uma oferta existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade Oferta para DTO de saída.
     */
    private OfertaOutputDTO toOutputDTO(Oferta o) {
        ResumoVendedorOfertaDTO  dto = new ResumoVendedorOfertaDTO(o.getVendedor().getId(), o.getVendedor().getTelefone(), o.getVendedor().getChavePix());
        return new OfertaOutputDTO(o.getId(), o.getTitulo(), o.getDescricao(),o.getDispStatus(), dto);
    }

    /**
     * Converte do DTO de entrada para entidade.
     */
    private Oferta toEntity(OfertaInputDTO dto) {
        Oferta o = new Oferta();
        o.setTitulo(dto.getTitulo());
        o.setDescricao(dto.getDescricao());
        Vendedor v = vendedorService.buscarEntidadePorId(dto.getIdVendedor());
        o.setVendedor(v);
        return o;
    }
}
