package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.ParticipanteInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.ParticipanteOutputDTO;

import br.edu.ifsc.sistemafeiracoletiva.service.ParticipanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade Participante.
 */
@RestController
@RequestMapping("/api/participantes")
@CrossOrigin(origins = "http://localhost:5500")
public class ParticipanteController {
    @Autowired // Injeta automaticamente o serviço
    private ParticipanteService service;

    /**
     * Retorna todos os participantes cadastrados.
     * @return lista de ParticipanteOutputDTO
     */
    @GetMapping
    public List<ParticipanteOutputDTO> listar() {
        return service.listar();
    }

//    /**
//     * Retorna todos as Participantes cadastrados e seus pedidos.
//     * @return lista de ParticipanteSeusPedidosOutputDTO
//     */
//    @GetMapping("/pedidos")
//    public List<ParticipanteSeusPedidosOutputDTO> listarParticipantesPedidos() {
//        return service.listarParticipantesPedidos();
//    }

    /**
     * Retorna uma participante por ID.
     * @param id identificador do participante
     * @return participante encontrado ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteOutputDTO> buscarPorId(@PathVariable int id) {
        Optional<ParticipanteOutputDTO> participante = service.buscarPorId(id);
        return participante.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    /**
//     * Retorna um participante por ID.
//     * @param id identificador a participante e seus pedidos
//     * @return participante encontrado ou 404
//     */
//    @GetMapping("pedidos/{id}")
//    public ResponseEntity<ParticipanteSeusPedidosOutputDTO> buscarPorIdParticipantesPedidos(@PathVariable int id) {
//        Optional<ParticipanteSeusPedidosOutputDTO> participante = service.buscarPorIdParticipantesPedidos(id);
//        return participante.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    /**
     * Cadastra uma nova participante.
     * @param dto objeto recebido no corpo da requisição
     * @return participante salva
     */
    @PostMapping
    public ResponseEntity<ParticipanteOutputDTO> criar(@RequestBody @Valid ParticipanteInputDTO dto) {
        ParticipanteOutputDTO salvo = service.salvar(dto, null);
        URI location = URI.create("/api/participantes/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    /**
     * Cadastra vários participantes de uma vez.
     * @param dtos lista de objetos a serem cadastrados
     * @return lista de participantes salvos
     */
    @PostMapping("/lote")
    public ResponseEntity<List<ParticipanteOutputDTO>> criarLote(@RequestBody List<ParticipanteInputDTO> dtos) {
        List<ParticipanteOutputDTO> salvos = service.salvarTodos(dtos);
        return ResponseEntity.ok(salvos);
    }

    /**
     * Atualiza os dados de uma participante existente.
     * @param id identificador da participante
     * @param dto dados atualizados
     * @return participante atualizado ou 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteOutputDTO> atualizar(@PathVariable int id, @RequestBody @Valid ParticipanteInputDTO dto) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        ParticipanteOutputDTO atualizado = service.salvar(dto, id);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove uma participante do sistema.
     * @param id identificador da participante
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
