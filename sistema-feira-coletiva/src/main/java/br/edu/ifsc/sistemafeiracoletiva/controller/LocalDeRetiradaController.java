package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.LocalDeRetiradaInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.LocalDeRetiradaOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.service.LocalDeRetiradaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade LocalDeRetirada.
 */
@RestController
@RequestMapping("/api/local-de-retirada")
@CrossOrigin(origins = "http://localhost:5500")
public class LocalDeRetiradaController {

    @Autowired
    private LocalDeRetiradaService service;

    /**
     * Retorna todos os locais de retirada cadastrados.
     * @return lista de LocalDeRetiradaOutputDTO
     */
    @GetMapping
    public List<LocalDeRetiradaOutputDTO> listar() {
        return service.listar();
    }

    /**
     * Retorna um local de retirada por ID.
     * @param id identificador do local de retirada
     * @return local de retirada encontrado ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocalDeRetiradaOutputDTO> buscarPorId(@PathVariable int id) {
        Optional<LocalDeRetiradaOutputDTO> localDeRetirada = service.buscarPorId(id);
        return localDeRetirada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cadastra um novo local de retirada.
     * @param dto objeto recebido no corpo da requisição
     * @return local de retirada salvo
     */
    @PostMapping
    public ResponseEntity<LocalDeRetiradaOutputDTO> criar(@RequestBody @Valid LocalDeRetiradaInputDTO dto) {
        LocalDeRetiradaOutputDTO salvo = service.salvar(dto, null);
        URI location = URI.create("/api/local-de-retirada/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    /**
     * Cadastra vários locais de retirada de uma vez.
     * @param dtos lista de objetos a serem cadastrados
     * @return lista de locais de retirada salvos
     */
    @PostMapping("/lote")
    public ResponseEntity<List<LocalDeRetiradaOutputDTO>> criarLote(@RequestBody List<LocalDeRetiradaInputDTO> dtos) {
        List<LocalDeRetiradaOutputDTO> salvos = service.salvarTodos(dtos);
        return ResponseEntity.ok(salvos);
    }

    /**
     * Atualiza os dados de um local de retirada existente.
     * @param id identificador do local de retirada
     * @param dto dados atualizados
     * @return local de retirada atualizado ou 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocalDeRetiradaOutputDTO> atualizar(@PathVariable int id, @RequestBody @Valid LocalDeRetiradaInputDTO dto) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        LocalDeRetiradaOutputDTO atualizado = service.salvar(dto, id);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove um local de retirada do sistema.
     * @param id identificador do local de retirada
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
