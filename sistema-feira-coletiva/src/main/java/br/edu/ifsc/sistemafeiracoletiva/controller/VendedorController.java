package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.VendedorInputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.VendedorOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.VendedorSuasOfertasOutputDTO;
import br.edu.ifsc.sistemafeiracoletiva.service.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar os endpoints da entidade Vendedor.
 */
@RestController
@RequestMapping("/api/vendedores")
@CrossOrigin(origins = "http://localhost:5500")
public class VendedorController {

    @Autowired // Injeta automaticamente o serviço
    private VendedorService service;

    /**
     * Retorna todos os vendedores cadastrados.
     * @return lista de VendedorOutputDTO
     */
    @GetMapping
    public List<VendedorOutputDTO> listar() {
        return service.listar();
    }

    /**
     * Retorna todos os vendedores cadastrados e suas ofertas.
     * @return lista de VendedorOutputDTO
     */
    @GetMapping("/ofertas")
    public List<VendedorSuasOfertasOutputDTO> listarVendedoresOfertas() {
        return service.listarVendedoresOfertas();
    }

    /**
     * Retorna um vendedor por ID.
     * @param id identificador do vendedor
     * @return vendedor encontrado ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<VendedorOutputDTO> buscarPorId(@PathVariable int id) {
        Optional<VendedorOutputDTO> vendedor = service.buscarPorId(id);
        return vendedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retorna um vendedor por ID.
     * @param id identificador do vendedor e suas ofertas
     * @return vendedor encontrado ou 404
     */
    @GetMapping("ofertas/{id}")
    public ResponseEntity<VendedorSuasOfertasOutputDTO> buscarPorIdVendedoresOfertas(@PathVariable int id) {
        Optional<VendedorSuasOfertasOutputDTO> vendedor = service.buscarPorIdVendedoresOfertas(id);
        return vendedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cadastra um novo vendedor.
     * @param dto objeto recebido no corpo da requisição
     * @return vendedor salvo
     */
    @PostMapping
    public ResponseEntity<VendedorOutputDTO> criar(@RequestBody @Valid VendedorInputDTO dto) {
        VendedorOutputDTO salvo = service.salvar(dto, null);
        URI location = URI.create("/api/vendedores/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    /**
     * Cadastra vários vendedores de uma vez.
     * @param dtos lista de objetos a serem cadastrados
     * @return lista de vendedores salvos
     */
    @PostMapping("/lote")
    public ResponseEntity<List<VendedorOutputDTO>> criarLote(@RequestBody List<VendedorInputDTO> dtos) {
        List<VendedorOutputDTO> salvos = service.salvarTodos(dtos);
        return ResponseEntity.ok(salvos);
    }

    /**
     * Atualiza os dados de um vendedor existente.
     * @param id identificador do vendedor
     * @param dto dados atualizados
     * @return vendedor atualizado ou 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<VendedorOutputDTO> atualizar(@PathVariable int id, @RequestBody @Valid VendedorInputDTO dto) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        VendedorOutputDTO atualizado = service.salvar(dto, id);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove um vendedor do sistema.
     * @param id identificador do vendedor
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

