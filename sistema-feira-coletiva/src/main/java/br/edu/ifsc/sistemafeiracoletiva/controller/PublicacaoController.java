package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.PublicacaoInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.PublicacaoOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.service.PublicacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade Publicacao.
 */
@RestController
@RequestMapping("/api/publicacoes")
@CrossOrigin(origins = "http://localhost:5500")
public class PublicacaoController {

    @Autowired
    private PublicacaoService service;

    /**
     * Retorna todos os publicacoes cadastrados.
     * @return lista de PublicacaoOutputDTO
     */
    @GetMapping
    public List<PublicacaoOutputDTO> listar() {
        return service.listar();
    }

    /**
     * Retorna as publicações de um vendedor específico.
     * @param vendedorId O ID do vendedor.
     * @return lista de PublicacaoOutputDTO do vendedor.
     */
    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<PublicacaoOutputDTO>> listarPorVendedor(@PathVariable Integer vendedorId) {
        List<PublicacaoOutputDTO> publicacoes = service.listarPorVendedor(vendedorId);
        if (publicacoes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicacoes);
    }

    /**
     * Retorna um publicacao por ID.
     * @param id identificador do publicacao
     * @return publicacao encontrado ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublicacaoOutputDTO> buscarPorId(@PathVariable int id) {
        Optional<PublicacaoOutputDTO> publicacao = service.buscarPorId(id);
        return publicacao.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cadastra um novo publicacao.
     * @param dto objeto recebido no corpo da requisição
     * @return publicacao salvo
     */
    @PostMapping
    public ResponseEntity<PublicacaoOutputDTO> criar(@RequestBody @Valid PublicacaoInputDTO dto) {
        PublicacaoOutputDTO salvo = service.salvar(dto, null);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    /**
     * ✅ RE-ADICIONADO: Cadastra vários publicacoes de uma vez.
     * @param dtos lista de objetos a serem cadastrados
     * @return lista de publicacoes salvos
     */
    @PostMapping("/lote")
    public ResponseEntity<List<PublicacaoOutputDTO>> criarLote(@RequestBody List<PublicacaoInputDTO> dtos) {
        List<PublicacaoOutputDTO> salvos = service.salvarTodos(dtos);
        return ResponseEntity.ok(salvos);
    }

    /**
     * ✅ RE-ADICIONADO: Atualiza os dados de um publicacao existente.
     * @param id identificador do publicacao
     * @param dto dados atualizados
     * @return publicacao atualizado ou 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<PublicacaoOutputDTO> atualizar(@PathVariable int id, @RequestBody @Valid PublicacaoInputDTO dto) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        PublicacaoOutputDTO atualizado = service.salvar(dto, id);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * ✅ RE-ADICIONADO: Remove um publicacao do sistema.
     * @param id identificador do publicacao
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
