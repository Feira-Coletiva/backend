package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.ProdutoInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.ProdutoOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade Produto.
 */
@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "http://localhost:5500")
public class ProdutoController {

    @Autowired // Injeta automaticamente o serviço
    private ProdutoService service;

    /**
     * Retorna todos os produtos cadastrados.
     * @return lista de ProdutosOutputDTO
     */
    @GetMapping
    public List<ProdutoOutputDTO> listar() {
        return service.listar();
    }

    /**
     * Retorna uma produto por ID.
     * @param id identificador do produto
     * @return produto encontrado ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoOutputDTO> buscarPorId(@PathVariable int id) {
        Optional<ProdutoOutputDTO> oferta = service.buscarPorId(id);
        return oferta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cadastra uma nova produto.
     * @param dto objeto recebido no corpo da requisição
     * @return produto salva
     */
    @PostMapping
    public ResponseEntity<ProdutoOutputDTO> criar(@RequestBody @Valid ProdutoInputDTO dto) {
        ProdutoOutputDTO salvo = service.salvar(dto, null);
        URI location = URI.create("/api/produtos/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    /**
     * Cadastra vários produtos de uma vez.
     * @param dtos lista de objetos a serem cadastrados
     * @return lista de produtos salvos
     */
    @PostMapping("/lote")
    public ResponseEntity<List<ProdutoOutputDTO>> criarLote(@RequestBody List<ProdutoInputDTO> dtos) {
        List<ProdutoOutputDTO> salvos = service.salvarTodos(dtos);
        return ResponseEntity.ok(salvos);
    }

//    @PostMapping("/lote/oferta/{idOferta}")
//    public ResponseEntity<List<ProdutoOutputDTO>> criarLoteEmOferta(
//            @PathVariable int idOferta,
//            @RequestBody List<ProdutoInputDTO> dtos) {
//
//        Oferta oferta = ofertaService.buscarEntidadePorId(idOferta);
//
//        List<Produto> produtos = dtos.stream()
//                .map(dto -> produtoService.toEntity(dto, oferta)) // AQUI USAMOS O MÉTODO
//                .collect(Collectors.toList());
//
//        List<ProdutoOutputDTO> salvos = produtoService.salvarTodos(produtos);
//
//        return ResponseEntity.ok(salvos);
//    }

    /**
     * Atualiza os dados de uma produto existente.
     * @param id identificador do produto
     * @param dto dados atualizados
     * @return produto atualizado ou 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoOutputDTO> atualizar(@PathVariable int id, @RequestBody @Valid ProdutoInputDTO dto) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        ProdutoOutputDTO atualizado = service.salvar(dto, id);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove uma produto do sistema.
     * @param id identificador da produto
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

