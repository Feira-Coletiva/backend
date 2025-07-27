package br.edu.ifsc.sistemafeiracoletiva.service;

import br.edu.ifsc.sistemafeiracoletiva.dto.ClienteInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.ClienteOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Cliente;
import br.edu.ifsc.sistemafeiracoletiva.model.domain.Vendedor;
import br.edu.ifsc.sistemafeiracoletiva.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de serviço para Cliente.
 * Responsável por conter as regras de negócio e chamadas ao repository.
 */
@Service    // Indica ao Spring que esta classe é um "Service" e pode ser injetada em outras classes
public class ClienteService {

    @Autowired  // Injeta automaticamente uma instância de ClienteRepository
    private ClienteRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder; // injeta o BCrypt configurado no SecurityConfig


    /**
     * Lista todos os clientes, convertendo as entidades para DTOs antes de devolver.
     */
    public List<ClienteOutputDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um cliente por ID e devolve um Optional<ClienteOutputDTO>.
     */
    public Optional<ClienteOutputDTO> buscarPorId(int id) {
        return repository.findById(id)
                .map(this::toOutputDTO);
    }

    /**
     * Busca um vendedor por ID e devolve um entidade de vendedor.
     */
    public Cliente buscarEntidadePorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    /**
     * Busca um cliente por ID e devolve uma entidade Cliente.
     */
    public Cliente buscarClientePorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }



    /**
     * Salva um novo cliente ou atualiza um cliente existente.
     * Retorna o DTO da entidade salva.
     */
    public ClienteOutputDTO salvar(ClienteInputDTO dto, Integer id) {
        Cliente cliente = toEntity(dto);
        if (id != null) {
            cliente.setId(id); // Atualização
        }
        // Criptografa a senha ANTES de salvar
        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);

        Cliente salvo = repository.save(cliente);
        return toOutputDTO(salvo);
    }

    /**
     * Salva uma lista de clientes (ex: cadastro em lote).
     */
    public List<ClienteOutputDTO> salvarTodos(List<ClienteInputDTO> dtos) {
        List<Cliente> clientes = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(clientes)
                .stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Remove um cliente pelo ID.
     */
    public void deletar(int id) {
        repository.deleteById(id);
    }

    /**
     * Verifica se um cliente existe por ID.
     */
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    /**
     * Converte uma entidade Cliente para DTO de saída.
     */
    private ClienteOutputDTO toOutputDTO(Cliente c) {
        return new ClienteOutputDTO(c.getId(), c.getNome(), c.getEmail(), c.getTelefone());
    }

    /**
     * Converte do DTO de entrada para entidade.
     */
    private Cliente toEntity(ClienteInputDTO dto) {
        Cliente c = new Cliente();
        c.setNome(dto.getNome());
        c.setEmail(dto.getEmail());
        c.setTelefone(dto.getTelefone());
        c.setSenha(dto.getSenha());
        return c;
    }
}

