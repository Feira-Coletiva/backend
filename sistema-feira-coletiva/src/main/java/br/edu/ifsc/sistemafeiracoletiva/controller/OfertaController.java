package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.OfertaInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.OfertaOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.service.OfertaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade Oferta.
 */
@RestController
@RequestMapping("/api/ofertas")
@CrossOrigin(origins = "http://localhost:5500")
public class OfertaController {

    @Autowired // Injeta automaticamente o serviço
    private OfertaService service;

    /**
     * Retorna todos os ofertas cadastrados.
     * @return lista de OfertaOutputDTO
     */
    @GetMapping
    public List<OfertaOutputDTO> listar() {
        return service.listar();
    }

    /**
     * Retorna uma oferta por ID.
     * @param id identificador do oferta
     * @return oferta encontrado ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<OfertaOutputDTO> buscarPorId(@PathVariable int id) {
        Optional<OfertaOutputDTO> oferta = service.buscarPorId(id);
        return oferta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cadastra uma nova oferta.
     * @param dto objeto recebido no corpo da requisição
     * @return oferta salva
     */
    @PostMapping
    public ResponseEntity<OfertaOutputDTO> criar(@RequestBody @Valid OfertaInputDTO dto) {
        OfertaOutputDTO salvo = service.salvar(dto, null);
        URI location = URI.create("/api/ofertas/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    /**
     * Cadastra vários ofertas de uma vez.
     * @param dtos lista de objetos a serem cadastrados
     * @return lista de ofertas salvos
     */
    @PostMapping("/lote")
    public ResponseEntity<List<OfertaOutputDTO>> criarLote(@RequestBody List<OfertaInputDTO> dtos) {
        List<OfertaOutputDTO> salvos = service.salvarTodos(dtos);
        return ResponseEntity.ok(salvos);
    }

    /**
     * Atualiza os dados de uma oferta existente.
     * @param id identificador da oferta
     * @param dto dados atualizados
     * @return oferta atualizado ou 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<OfertaOutputDTO> atualizar(@PathVariable int id, @RequestBody @Valid OfertaInputDTO dto) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        OfertaOutputDTO atualizado = service.salvar(dto, id);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove uma oferta do sistema.
     * @param id identificador da oferta
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
