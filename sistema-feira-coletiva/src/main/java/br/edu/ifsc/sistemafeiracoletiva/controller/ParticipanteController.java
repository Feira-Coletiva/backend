package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.ParticipanteInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.ParticipanteOutputDTO;

import br.edu.ifsc.sistemafeiracoletiva.service.ParticipanteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade Participante.
 */
@Slf4j
@RestController
@RequestMapping("/api/participantes")
@CrossOrigin(origins = "http://localhost:5500") // Permita o seu frontend
public class ParticipanteController {

    @Autowired
    private ParticipanteService service;

    /**
     * Lista todas as participações.
     * @return Lista de ParticipanteOutputDTO.
     */
    @GetMapping
    public List<ParticipanteOutputDTO> listar() {
        return service.listar();
    }

    /**
     * Busca uma participação por ID.
     * @param id ID da participação.
     * @return ParticipanteOutputDTO ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteOutputDTO> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todas as participações de um cliente específico.
     * @param clienteId ID do cliente.
     * @return Lista de ParticipanteOutputDTO do cliente.
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ParticipanteOutputDTO>> listarPorCliente(@PathVariable Integer clienteId) {
        List<ParticipanteOutputDTO> participantes = service.listarPorCliente(clienteId);
        if (participantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(participantes);
    }

    /**
     * Cria uma nova participação com seus pedidos.
     * @param dto ParticipanteInputDTO com os dados da participação e pedidos.
     * @return ParticipanteOutputDTO salvo com status 201 Created.
     */
    @PostMapping
    public ResponseEntity<ParticipanteOutputDTO> criar(@RequestBody @Valid ParticipanteInputDTO dto) {
        try {
            ParticipanteOutputDTO salvo = service.criarParticipacao(dto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(salvo.getId()).toUri();
            return ResponseEntity.created(location).body(salvo);
        } catch (IllegalArgumentException e) {
            // Captura erros de validação de negócio (ex: estoque insuficiente, produto não encontrado)
            return ResponseEntity.badRequest().body(null); // Poderíamos retornar um DTO de erro mais específico
        } catch (Exception e) {
            log.error("Erro ao criar participação: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
