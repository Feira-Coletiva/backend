package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.PedidoInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.PedidoOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade Sacola.
 */
@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5500")
public class PedidoController {
//    @Autowired // Injeta automaticamente o serviço
//    private PedidoService service;
//
//    /**
//     * Retorna todos os pedidos cadastrados.
//     * @return lista de SacolasOutputDTO
//     */
//    @GetMapping
//    public List<PedidoOutputDTO> listar() {
//        return service.listar();
//    }
//
//    /**
//     * Retorna uma pedido por ID.
//     * @param id identificador do pedido
//     * @return pedido encontrado ou 404
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<PedidoOutputDTO> buscarPorId(@PathVariable int id) {
//        Optional<PedidoOutputDTO> pedido = service.buscarPorId(id);
//        return pedido.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    /**
//     * Cadastra uma nova pedido.
//     * @param dto objeto recebido no corpo da requisição
//     * @return pedido salva
//     */
//    @PostMapping
//    public ResponseEntity<PedidoOutputDTO> criar(@RequestBody @Valid PedidoInputDTO dto) {
//        PedidoOutputDTO salvo = service.salvar(dto, null);
//        URI location = URI.create("/api/pedidos/" + salvo.getId());
//        return ResponseEntity.created(location).body(salvo);
//    }
//
//    /**
//     * Cadastra vários pedidos de uma vez.
//     * @param dtos lista de objetos a serem cadastrados
//     * @return lista de pedidos salvos
//     */
//    @PostMapping("/lote")
//    public ResponseEntity<List<PedidoOutputDTO>> criarLote(@RequestBody List<PedidoInputDTO> dtos) {
//        List<PedidoOutputDTO> salvos = service.salvarTodos(dtos);
//        return ResponseEntity.ok(salvos);
//    }
//
//    /**
//     * Atualiza os dados de uma pedido existente.
//     * @param id identificador do pedido
//     * @param dto dados atualizados
//     * @return pedido atualizado ou 404
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity<PedidoOutputDTO> atualizar(@PathVariable int id, @RequestBody @Valid PedidoInputDTO dto) {
//        if (!service.existePorId(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        PedidoOutputDTO atualizado = service.salvar(dto, id);
//        return ResponseEntity.ok(atualizado);
//    }
//
//    /**
//     * Remove uma pedido do sistema.
//     * @param id identificador da pedido
//     * @return status HTTP apropriado
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> remover(@PathVariable int id) {
//        if (!service.existePorId(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        service.deletar(id);
//        return ResponseEntity.noContent().build();
//    }
}
