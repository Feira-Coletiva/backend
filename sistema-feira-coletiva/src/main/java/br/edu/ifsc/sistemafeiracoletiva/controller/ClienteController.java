package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.ClienteInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.ClienteOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade Cliente.
 */
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:5500")
public class ClienteController {

    @Autowired // Injeta automaticamente o serviço
    private ClienteService service;

    /**
     * Retorna todos os clientes cadastrados.
     * @return lista de ClienteOutputDTO
     */
    @GetMapping
    public List<ClienteOutputDTO> listar() {
        return service.listar();
    }

    /**
     * Retorna um cliente por ID.
     * @param id identificador do cliente
     * @return cliente encontrado ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteOutputDTO> buscarPorId(@PathVariable int id) {
        Optional<ClienteOutputDTO> cliente = service.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cadastra um novo cliente.
     * @param dto objeto recebido no corpo da requisição
     * @return cliente salvo
     */
    @PostMapping
    public ResponseEntity<ClienteOutputDTO> criar(@RequestBody @Valid ClienteInputDTO dto) {
        ClienteOutputDTO salvo = service.salvar(dto, null);
        return ResponseEntity.ok(salvo);
    }

    /**
     * Cadastra vários clientes de uma vez.
     * @param dtos lista de objetos a serem cadastrados
     * @return lista de clientes salvos
     */
    @PostMapping("/lote")
    public ResponseEntity<List<ClienteOutputDTO>> criarLote(@RequestBody List<ClienteInputDTO> dtos) {
        List<ClienteOutputDTO> salvos = service.salvarTodos(dtos);
        return ResponseEntity.ok(salvos);
    }

    /**
     * Atualiza os dados de um cliente existente.
     * @param id identificador do cliente
     * @param dto dados atualizados
     * @return cliente atualizado ou 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteOutputDTO> atualizar(@PathVariable int id, @RequestBody @Valid ClienteInputDTO dto) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        ClienteOutputDTO atualizado = service.salvar(dto, id);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove um cliente do sistema.
     * @param id identificador do cliente
     * @return status HTTP apropriado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable int id) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


