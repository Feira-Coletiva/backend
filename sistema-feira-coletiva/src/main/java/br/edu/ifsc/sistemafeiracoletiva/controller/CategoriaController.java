package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.CategoriaInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.CategoriaOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade Categoria.
 */
@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:5500")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    /**
     * Retorna todos os categoria cadastradas.
     * @return lista de CategoriaOutputDTO
     */
    @GetMapping
    public List<CategoriaOutputDTO> listar() {
        return service.listar();
    }

    /**
     * Retorna um categoria por ID.
     * @param id identificador do categoria
     * @return categoria encontrado ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaOutputDTO> buscarPorId(@PathVariable int id) {
        Optional<CategoriaOutputDTO> categoria = service.buscarPorId(id);
        return categoria.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cadastra um novo categoria.
     * @param dto objeto recebido no corpo da requisição
     * @return categoria salvo
     */
    @PostMapping
    public ResponseEntity<CategoriaOutputDTO> criar(@RequestBody @Valid CategoriaInputDTO dto) {
        CategoriaOutputDTO salvo = service.salvar(dto, null);
        URI location = URI.create("/api/categorias/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    /**
     * Cadastra vários categorias de uma vez.
     * @param dtos lista de objetos a serem cadastrados
     * @return lista de categorias salvos
     */
    @PostMapping("/lote")                                      // @Valid Verificar validação para cadastros multiplos
    public ResponseEntity<List<CategoriaOutputDTO>> criarLote(@RequestBody List<CategoriaInputDTO> dtos) {
        List<CategoriaOutputDTO> salvos = service.salvarTodos(dtos);
        return ResponseEntity.ok(salvos);
    }

    /**
     * Atualiza os dados de um categoria existente.
     * @param id identificador do categoria
     * @param dto dados atualizados
     * @return categoria atualizado ou 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaOutputDTO> atualizar(@PathVariable int id, @RequestBody @Valid CategoriaInputDTO dto) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        CategoriaOutputDTO atualizado = service.salvar(dto, id);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove um categoria do sistema.
     * @param id identificador do categoria
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
