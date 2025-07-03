package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.ResumoOfertasVendedorDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.VendedorInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.VendedorOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.VendedorSuasOfertasOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Vendedor;
import br.edu.ifsc.sistemafeiracoletiva.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Vendedor.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Service
public class VendedorService {

    @Autowired
    private VendedorRepository repository;

    /**
     * Lista todos os vendedores, convertendo as entidades para DTOs antes de devolver.
     */
    public List<VendedorOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os vendedores e suas ofertas, convertendo as entidades para DTOs antes de devolver.
     */
    public List<VendedorSuasOfertasOutputDTO> listarVendedoresOfertas() {
        return repository.findAll()
                .stream()
                .map(this::toOutputMoreOfertasDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um vendedor por ID e devolve um Optional<VendedorOutputDTO>.
     */
    public Optional<VendedorOutputDTO> buscarPorId(int id) {
        return repository.findById(id)
                .map(this::toOutputDTO);
    }

    /**
     * Busca um vendedor por ID e devolve um Optional<VendedorSuasOfertasOutputDTO>.
     */
    public Optional<VendedorSuasOfertasOutputDTO> buscarPorIdVendedoresOfertas(int id) {
        return repository.findById(id)
                .map(this::toOutputMoreOfertasDTO);
    }

    /**
     * Busca um vendedor por ID e devolve um entidade de vendedor.
     */
    public Vendedor buscarEntidadePorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
    }

    /**
     * Salva um novo vendedor ou atualiza um vendedor existente.
     * Retorna o DTO da entidade salva.
     */
    public VendedorOutputDTO salvar(VendedorInputDTO dto, Integer id) {
        Vendedor vendedor = toEntity(dto);
        if (id != null) {
            vendedor.setId(id); // Atualização
        }
        Vendedor salvo = repository.save(vendedor);
        return toOutputDTO(salvo);
    }

    /**
     * Salva uma lista de vendedor (ex: cadastro em lote).
     */
    public List<VendedorOutputDTO> salvarTodos(List<VendedorInputDTO> dtos) {
        List<Vendedor> vendedores = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(vendedores)
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Remove um vedendor pelo ID.
     */
    public void deletar(int id) {
        repository.deleteById(id);
    }

    /**
     * Verifica se um vendedor existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade Vendedor para DTO de saída.
     */
    private VendedorOutputDTO toOutputDTO(Vendedor v) {
        return new VendedorOutputDTO(v.getId(), v.getNome(), v.getTelefone(), v.getRegDeAtuacao(), v.getChavePix());
    }

    /**
     * Converte uma entidade Vendedor para DTO de saída com orfetas.
     */
    private VendedorSuasOfertasOutputDTO toOutputMoreOfertasDTO(Vendedor v) {
        List<ResumoOfertasVendedorDTO> os = v.getOfertas().stream()
                .map(o -> new ResumoOfertasVendedorDTO(o.getId(), o.getTitulo(), o.getDispStatus()))
                .collect(Collectors.toList());
        return new VendedorSuasOfertasOutputDTO(v.getId(), v.getNome(), os);
    }

    /**
     * Converte do DTO de entrada para entidade.
     */
    private Vendedor toEntity(VendedorInputDTO dto) {
        Vendedor v = new Vendedor();
        v.setNome(dto.getNome());
        v.setTelefone(dto.getTelefone());
        v.setSenha(dto.getSenha());
        v.setRg(dto.getRg());
        v.setCep(dto.getCep());
        v.setRegDeAtuacao(dto.getRegDeAtuacao());
        v.setChavePix(dto.getChavePix());
        return v;
    }
}

